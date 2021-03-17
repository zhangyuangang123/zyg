package com.zyg.core.base;

import lombok.Getter;

/**
 * @ClassName ResultCodeEnum
 * @Author PGT
 * @Description 结果类枚举
 * @Date 2020-05-06 下午 01:34
 **/
@Getter
public enum ResultCodeEnum {

    SUCCESS(true, 200, "成功",10000),
    FAIL(false,200,"请重试",10000),
    SIGN_ERROR(true, 201, "请重新登录",201),
    ERROR_REQUEST(false, 400, "缺少必填参数",400),

    // jwt
    ERROR_IDENTITY(false, 401, "需要验证身份（登录）",401),
    ERROR_PERMISSIONS(false, 403, "身份有效，但权限不足",403),
    ERROR_RESOURCES(false, 404, "请求的资源不存在",404),
    ERROR_METHODS(false, 405, "针对这个资源所请求的方法被禁止",405),
    ERROR_NOT_LOG_IN(false, 406, "未登录",406),
    ERROR_LOGIN_DATE(false, 407, "登录过期",407),

    // sys
    ERROR_SYSTEM(false, 500, "系统繁忙",30000);


    // 响应是否成功
    private Boolean success;
    // 响应状态码
    private Integer statusCode;
    // 响应信息
    private String message;

    private Integer code;

    ResultCodeEnum(boolean success, Integer statusCode, String message, Integer code) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
        this.code = code;
    }
}
