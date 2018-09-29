package cn.anytec.anguan.controller;

import cn.anytec.anguan.httpconfig.ServerResponse;
import cn.anytec.anguan.po.User;
import cn.anytec.anguan.service.inf.BaseService;
import cn.anytec.anguan.service.inf.IUser;
import cn.hutool.http.HttpStatus;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by imyzt on 2018/9/26 9:58
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户管理功能", tags = "用户管理功能")
public class UserController extends BaseControllerImpl<Integer, User, String[], Page<User>> {

    @Autowired
    private IUser userService;

    @Override
    public BaseService<Integer, User, String[], Page<User>> getService() {
        return userService;
    }

    @Override
    String getFormErrorMsg() {
        return "传入参数为空";
    }

    @Override
    String getVoErrorMsg() {
        return "操作失败";
    }

    /** 用于前台感知当前用户未登录 */
    @GetMapping("unauth")
    public ServerResponse unauth() {
        return ServerResponse.createByErrorCodeMessage(HttpStatus.HTTP_FORBIDDEN, "未登录");
    }

    @PostMapping("login")
    public ServerResponse login(String uname, String upass) {

        UsernamePasswordToken token = new UsernamePasswordToken(uname, upass);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);

        return ServerResponse.createBySuccess("登录成功");
    }

    @PostMapping("logout")
    public ServerResponse logout() {
        SecurityUtils.getSubject().logout();
        return ServerResponse.createBySuccessMessage("OK");
    }

    @GetMapping("{uid}/roles")
    public ServerResponse findRoleByUserId(@PathVariable Integer uid) {
        List roles = userService.findRoleByUserId(uid);
        if (roles != null) {
            return ok(roles);
        }
        return fail("该用户没有任何角色信息");
    }
}
