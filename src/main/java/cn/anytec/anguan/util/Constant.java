package cn.anytec.anguan.util;

/**
 * Created by imyzt on 2018/9/26 12:20
 */
public interface Constant {

    interface UserStatus {
        /** 启用 */
        Integer ENABLE = 1;
        /** 删除 */
        Integer DELETED = 2;
        /** 禁用 */
        Integer DISABLE = 3;
    }
    interface ME {
        /** 登录加密盐值 */
        String SALT = "fsd*{&$%%#!~jl:DASE";
    }
    interface INIT_DATE {


    }
}
