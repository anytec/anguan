package cn.anytec.anguan.controller;

import cn.anytec.anguan.httpconfig.ServerResponse;
import cn.anytec.anguan.po.Permission;
import cn.anytec.anguan.service.inf.BaseService;
import cn.anytec.anguan.service.inf.IPermission;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by imyzt on 2018/9/27 17:22
 */
@RestController
@RequestMapping("permission")
@RequiresRoles("super")
@Api(value = "权限相关操作", tags = "权限相关操作")
public class PermissionController extends BaseControllerImpl<Integer,Permission,String, Page<Permission>> {

    private final Log log = LogFactory.get();

    @Autowired
    private IPermission permissionService;

    @Override
    public BaseService<Integer, Permission, String, Page<Permission>> getService() {
        super.setPage(0, 10);
        return permissionService;
    }

    @Override
    String getFormErrorMsg() {
        return "请求参数不能为空";
    }

    @Override
    String getVoErrorMsg() {
        return "没有查询到数据";
    }


    @PostMapping("addPermissions2Role")
    public ServerResponse addPermissions2Role(Integer rid, String permissions) {
        permissionService.addPermissions2Role(rid, permissions);
        return ok("添加成功");
    }

    @GetMapping("{pageNum}/{pageSize}/rolePermissions")
    public ServerResponse rolePermissions(@PathVariable Integer pageNum, @PathVariable Integer pageSize,
                                          String rname) {
        List list = permissionService.rolePermissions(pageNum, pageSize, rname);
        return assertVo(list);
    }

    private ServerResponse assertVo(List result) {
        if (result == null) {
            return fail("没有查询到任何信息");
        }
        return ok(result);
    }
}
