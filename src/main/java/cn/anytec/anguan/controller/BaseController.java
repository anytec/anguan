package cn.anytec.anguan.controller;

import cn.anytec.anguan.httpconfig.ServerResponse;

import java.io.Serializable;

/**
 * Created by imyzt on 2018/9/27 17:31
 * I 主键
 * T 实体
 * Q 查询条件
 * P 分页对象
 */
public interface BaseController<I, T extends Serializable, Q> {



    ServerResponse<T> delById(I id);

    /** 修改部分字段 */
    ServerResponse<T> edit(T data);

    /** 全量更新 */
    ServerResponse<T> update(T data);

    ServerResponse<T> save(T data);

    ServerResponse<T> findById(I id);

    ServerResponse<T> list(Integer pageNum, Integer pageSize, Q query);

    void assertForm(T data);

    ServerResponse<T> assertVo(T result);

    ServerResponse<T> ok();

    ServerResponse<T> ok(String msg);

    ServerResponse<T> ok(T data);

    ServerResponse<T> ok(Object data);

    ServerResponse<T> fail();

    ServerResponse<T> fail(String msg);

}
