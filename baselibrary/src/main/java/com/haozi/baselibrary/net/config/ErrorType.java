package com.haozi.baselibrary.net.config;

public interface ErrorType {

    int ERROR_INVALID_USER = 401;
    int ERROR_SERVER = 1003;
    int ERROR_NETWORK = 1004;

    int ERROR_RESPONSE_NULL = 9988;
    int ERROR_OTHER = 9999;

    /**黑名单*/
    int ERROR_CHECK_BLACKLIST = 601;
    /**未登记*/
    int ERROR_CHECK_UNREGISTER = 602;
    /**用户不存在*/
    int ERROR_USER_NOEXSITS = 603;
    /**密码错误*/
    int ERROR_USER_PSWERROR = 604;

    int CODE_SUCCESS = 200;
}
