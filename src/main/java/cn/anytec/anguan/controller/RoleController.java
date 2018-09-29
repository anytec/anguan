package cn.anytec.anguan.controller;

import cn.anytec.anguan.httpconfig.ServerResponse;
import cn.anytec.anguan.po.Role;
import cn.anytec.anguan.service.inf.BaseService;
import cn.anytec.anguan.service.inf.IRole;
import cn.hutool.core.collection.CollectionUtil;
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
@RequestMapping("role")
@RequiresRoles("super")
@Api(value = "角色相关操作", tags = "角色相关操作")
public class RoleController extends BaseControllerImpl<Integer, Role, String, Page<Role>> {

    private final Log log = LogFactory.get();

    @Autowired
    private IRole roleService;

    @Override
    public BaseService<Integer, Role, String, Page<Role>> getService() {
        return roleService;
    }

    @Override
    String getFormErrorMsg() {
        return "请求参数不能为空";
    }

    @Override
    String getVoErrorMsg() {
        return "操作失败";
    }

    @GetMapping("{rid}/permissions")
    public ServerResponse findPermissionsByRoleId(@PathVariable Integer rid) {
        List permissions = roleService.findPermissionsByRoleId(rid);
        if (CollectionUtil.isNotEmpty(permissions)) {
            return ok(permissions);
        }
        return fail("该角色没有任何权限信息");
    }

    @PostMapping("addRoles2User")
    public ServerResponse addRoles2User(Integer uid, String roles) {
        roleService.addRoles2User(uid, roles);
        return ok("添加成功");
    }

    @DeleteMapping("clearRoles2User/{uid}")
    public ServerResponse clearRoles2User(@PathVariable Integer uid) {
        roleService.clearRoles2User(uid);
        return ok("删除成功");
    }

    @GetMapping("{pageNum}/{pageSize}/userRoles")
    public ServerResponse userRoles(@PathVariable Integer pageNum, @PathVariable Integer pageSize, String uname) {
        List list = roleService.usersRoles(pageNum, pageSize, uname);
        return assertVo(list);
    }

    private ServerResponse assertVo(List result) {
        if (result == null) {
            return fail("没有查询到任何信息");
        }
        return ok(result);
    }
}
