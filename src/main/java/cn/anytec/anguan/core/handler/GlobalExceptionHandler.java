package cn.anytec.anguan.core.handler;

import cn.anytec.anguan.core.exception.AnguanException;
import cn.anytec.anguan.httpconfig.ResponseCode;
import cn.anytec.anguan.httpconfig.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody ServerResponse exceptionHandler(Exception e) {

        // init
        int code = ResponseCode.ERROR.getCode();
        String message = e.getMessage();

        // 分支
        if (e instanceof AnguanException) {
            AnguanException anguanException = (AnguanException) e;
            code = anguanException.getCode();
            message = anguanException.getMessage();
        }

        e.printStackTrace();

        return ServerResponse.createByErrorCodeMessage(code, message);
    }

}
