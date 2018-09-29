package cn.anytec.anguan.controller;

import cn.anytec.anguan.core.exception.AnguanException;
import cn.anytec.anguan.httpconfig.ServerResponse;
import cn.anytec.anguan.service.inf.BaseService;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * Created by imyzt on 2018/9/27 18:02 <br/>
 * 约定: 任何问题全部通过{@link AnguanException} 进行抛出.{@link cn.anytec.anguan.core.handler.GlobalExceptionHandler} 会主动进行捕获处理,无需其他操作.
 */
public abstract class BaseControllerImpl<I, T extends Serializable, Q, P> implements BaseController<I, T, Q> {

    /**
     * 获取Service
     */
    public abstract BaseService<I, T, Q, P> getService();

    /**
     * 友好提示信息
     */
    abstract String getFormErrorMsg();
    abstract String getVoErrorMsg();

    private Integer pageNum = 1;
    private Integer pageSize = 10;

    /**
     * 重写分页参数
     * @param pageNum default=1
     * @param pageSize default=10
     */
    protected void setPage (Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    @DeleteMapping("{id}")
    public ServerResponse<T> delById(@PathVariable I id) {
        getService().del(id);
        return ServerResponse.createBySuccess();
    }

    @Override
    @PatchMapping
    public ServerResponse<T> edit(@RequestBody T data) {

        assertForm(data);
        T result = getService().edit(data);
        return assertVo(result);
    }

    @Override
    @PutMapping
    public ServerResponse<T> update(@RequestBody T data) {
        assertForm(data);
        T result = getService().update(data);
        return assertVo(result);
    }

    @Override
    @PostMapping
    public ServerResponse<T> save(T data) {

        assertForm(data);
        T result = getService().save(data);
        return assertVo(result);
    }

    @Override
    @GetMapping("{id}")
    public ServerResponse<T> findById(@PathVariable I id) {

        if (id == null) {
            throw new AnguanException(getFormErrorMsg());
        }
        T result = getService().findById(id);
        return assertVo(result);
    }

    @Override
    @GetMapping("{pageNum}/{pageSize}")
    public ServerResponse list(@PathVariable Integer pageNum,
                               @PathVariable Integer pageSize,
                               @RequestParam(required = false) Q query) {

        pageNum = pageNum < 0 ? this.pageNum : pageNum;
        pageSize = pageSize < 1 ? this.pageSize : pageSize;

        P result = getService().list(pageNum, pageSize, query);
        if (result == null) {
            ServerResponse.createByErrorMessage(getVoErrorMsg());
        }
        return ServerResponse.createBySuccess(result);
    }

    @Override
    public void assertForm(T data) {
        if (data == null) {
            throw new AnguanException(getFormErrorMsg());
        }
    }

    @Override
    public ServerResponse<T> assertVo(T result) {
        if (result == null) {
            return ServerResponse.createByErrorMessage(getVoErrorMsg());
        }
        return ServerResponse.createBySuccess(result);
    }

    @Override
    public ServerResponse<T> ok() {
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse<T> ok(String msg) {
        return ServerResponse.createBySuccessMessage(msg);
    }

    @Override
    public ServerResponse ok(Object data) {
        return ServerResponse.createBySuccess(data);
    }

    @Override
    public ServerResponse<T> ok(T data) {
        return ServerResponse.createBySuccess(data);
    }

    @Override
    public ServerResponse<T> fail() {
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse<T> fail(String msg) {
        return ServerResponse.createByErrorMessage(msg);
    }
}
