package cn.anytec.anguan.controller;

import cn.anytec.anguan.po.Camera;
import cn.anytec.anguan.httpconfig.ServerResponse;
import cn.anytec.anguan.service.inf.ICamera;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Api(value = "设备接口", tags = "设备操作接口")
@RequestMapping("camera")
public class CameraController {

    private static final Log log = LogFactory.get();

    @Autowired
    private ICamera service;

    @RequiresPermissions("delUser")
    @ApiOperation("查询设备列表")
    @GetMapping("")
    public ServerResponse findAll(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                  @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                  @RequestParam(required = false) String macAddress, @RequestParam(required = false) String pushIp) {

        macAddress = macAddress != null ? macAddress.trim() : macAddress;
        pushIp = pushIp != null ? pushIp.trim() : pushIp;
        Page result = service.findAll(pageNum, pageSize, macAddress, pushIp);
        if (null == result) {
            return ServerResponse.createByErrorMessage("【查找设备】 未找到任何设备");
        }
        return ServerResponse.createBySuccess(result);
    }

    @RequiresUser
    @ApiOperation("根据id查询单个设备详细信息")
    @GetMapping("{id}")
    public ServerResponse findOne(@PathVariable Integer id) {

        if (null == id) {
            log.error("【查找设备】 无法查找设备， 原因：id为空");
            return ServerResponse.createByErrorMessage("无法查找设备， 原因：id为空");
        }

        Optional<Camera> result = service.findOne(id);
        if (!result.isPresent()) {
            return ServerResponse.createByErrorMessage("【查找设备】 未找到该设备");
        }
        return ServerResponse.createBySuccess(result);
    }

    @ApiOperation("添加设备")
    @PostMapping("")
    public ServerResponse add(@Valid  Camera camera, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("【添加设备】 不能添加设备， 原因：" + bindingResult.getFieldError().getDefaultMessage());
            return ServerResponse.createByErrorMessage(bindingResult.getFieldError().getDefaultMessage());
        }

        Camera result = service.save(camera);
        if (null == result) {
            return ServerResponse.createByErrorMessage("【添加设备】 设备添加失败");
        }
        return ServerResponse.createBySuccess("设备添加成功", result);
    }

    @ApiOperation("修改设备")
    @PutMapping("")
    public ServerResponse update(@Valid @RequestBody Camera camera, BindingResult bindingResult) {

        if (null != camera && camera.getId() == null) {
            log.error("【修改设备】 不能修改设备， 原因： 设备id不能为空");
            return ServerResponse.createByErrorMessage("设备id不能为空");
        }
        if (bindingResult.hasErrors()) {
            log.error("【修改设备】 不能修改设备， 原因：" + bindingResult.getFieldError().getDefaultMessage());
            return ServerResponse.createByErrorMessage(bindingResult.getFieldError().getDefaultMessage());
        }

        Camera result = service.update(camera);
        if (null == result) {
            return ServerResponse.createByErrorMessage("【修改设备】 设备修改失败");
        }
        return ServerResponse.createBySuccess("设备修改成功", result);
    }

    @ApiOperation("根据id删除设备")
    @DeleteMapping("{id}")
    public ServerResponse delete(@PathVariable Integer id) {

        if (null == id) {
            log.error("【删除设备】 不能删除设备， 原因：id为空");
            return ServerResponse.createByErrorMessage("不能删除设备， 原因：id为空");
        }

        try {
            service.delete(id);
        }catch (EmptyResultDataAccessException e) {
            log.error("【删除设备】 不能删除设备， 原因：没有找到该设备, id=" + id);
            return ServerResponse.createByErrorMessage("没有找到该设备");
        }
        return ServerResponse.createBySuccess();
    }

}
