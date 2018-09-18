package cn.anytec.anguan.core.exception;

import cn.anytec.anguan.httpconfig.ResponseCode;

public class AnguanException extends RuntimeException {

    private int code;

    private String message;

    public AnguanException() {
        super();
        this.code = ResponseCode.ERROR.getCode();
        this.message = ResponseCode.ERROR.getDesc();
    }

    public AnguanException(ResponseCode responseCode) {
        super(responseCode.getDesc());
        this.code = responseCode.getCode();
        this.message = responseCode.getDesc();
    }

    public AnguanException(String message) {
        super(message);
        this.code = ResponseCode.ERROR.getCode();
        this.message = message;
    }

    public AnguanException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public AnguanException(Exception e) {
        this.code = ResponseCode.ERROR.getCode();
        this.message = e.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
