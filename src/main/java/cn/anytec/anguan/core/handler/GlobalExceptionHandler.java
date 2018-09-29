package cn.anytec.anguan.core.handler;

import cn.anytec.anguan.core.exception.AnguanException;
import cn.anytec.anguan.httpconfig.ResponseCode;
import cn.anytec.anguan.httpconfig.ServerResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Log log = LogFactory.get();

    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody ServerResponse exceptionHandler(Exception e) {

        // init
        int code = ResponseCode.ERROR.getCode();
        String message = e.getMessage();

        // 分支
        if (e instanceof AnguanException) {
            AnguanException exception = (AnguanException) e;
            code = exception.getCode();
            message = exception.getMessage();
        }

        if (e instanceof UnknownAccountException || e instanceof LockedAccountException) {
            AccountException exception = (AccountException) e;
            code = HttpStatus.HTTP_FORBIDDEN;
            message = exception.getMessage();
        }
        if (e instanceof IncorrectCredentialsException) {
            IncorrectCredentialsException exception = (IncorrectCredentialsException) e;
            code = HttpStatus.HTTP_FORBIDDEN;
            message = "用户名或密码错误";
        }
        if (e instanceof UnauthorizedException) {
            UnauthorizedException exception = (UnauthorizedException) e;
            code = HttpStatus.HTTP_FORBIDDEN;
            message = "没有访问权限";
        }
        if (e instanceof UnauthenticatedException) {
            UnauthenticatedException exception = (UnauthenticatedException) e;
            code = HttpStatus.HTTP_FORBIDDEN;
            message = "未登录";
        }
        log.error(e);
        return ServerResponse.createByErrorCodeMessage(code, message);
    }

}
