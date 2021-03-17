package com.zyg.core.base;

import lombok.Data;
import org.springframework.http.ResponseEntity;

/**
 * @ClassName BaseResponse
 * @Author PGT
 * @Description 统一结果类
 * @Date 2020-05-06 下午 01:40
 **/
@Data
public class BaseResponse {

    private static final String DATA = "data";

    private Boolean success;

    private Integer code;

    private String message;

    private Object data = new Object();

    // 构造器私有
    private BaseResponse() {
    }

    // 通用返回成功
    public static ResponseEntity<Object> sendMessageSuccess() {
        BaseResponse r = new BaseResponse();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return ResponseEntity.status(ResultCodeEnum.SUCCESS.getStatusCode()).body(r);
    }

    // 通用返回失败，系统错误
    public static ResponseEntity<Object> sendMessageError() {
        BaseResponse r = new BaseResponse();
        r.setSuccess(ResultCodeEnum.ERROR_SYSTEM.getSuccess());
        r.setCode(ResultCodeEnum.ERROR_SYSTEM.getCode());
        r.setMessage(ResultCodeEnum.ERROR_SYSTEM.getMessage());
        return ResponseEntity.status(ResultCodeEnum.ERROR_SYSTEM.getStatusCode()).body(r);
    }

    // 登录过期
    public static ResponseEntity<Object> sendLoginDateError() {
        BaseResponse r = new BaseResponse();
        r.setSuccess(ResultCodeEnum.ERROR_LOGIN_DATE.getSuccess());
        r.setCode(ResultCodeEnum.ERROR_LOGIN_DATE.getCode());
        r.setMessage(ResultCodeEnum.ERROR_LOGIN_DATE.getMessage());
        return ResponseEntity.status(ResultCodeEnum.ERROR_LOGIN_DATE.getStatusCode()).body(r);
    }

    // 通用返回失败，参数错误
    public static ResponseEntity<Object> sendMessageRequest() {
        BaseResponse r = new BaseResponse();
        r.setSuccess(ResultCodeEnum.ERROR_REQUEST.getSuccess());
        r.setCode(ResultCodeEnum.ERROR_REQUEST.getCode());
        r.setMessage(ResultCodeEnum.ERROR_REQUEST.getMessage());
        return ResponseEntity.status(ResultCodeEnum.ERROR_REQUEST.getStatusCode()).body(r);
    }

    // 通用返回失败，token失效异常
    public static ResponseEntity<Object> sendMessageIdentity() {
        BaseResponse r = new BaseResponse();
        r.setSuccess(ResultCodeEnum.ERROR_IDENTITY.getSuccess());
        r.setCode(ResultCodeEnum.ERROR_IDENTITY.getCode());
        r.setMessage(ResultCodeEnum.ERROR_IDENTITY.getMessage());
        return ResponseEntity.status(ResultCodeEnum.ERROR_IDENTITY.getStatusCode()).body(r);
    }
    // 通用返回失败，权限不足
    public static ResponseEntity<Object> sendMessagePermissions() {
        BaseResponse r = new BaseResponse();
        r.setSuccess(ResultCodeEnum.ERROR_PERMISSIONS.getSuccess());
        r.setCode(ResultCodeEnum.ERROR_PERMISSIONS.getCode());
        r.setMessage(ResultCodeEnum.ERROR_PERMISSIONS.getMessage());
        return ResponseEntity.status(ResultCodeEnum.ERROR_PERMISSIONS.getStatusCode()).body(r);
    }

    // 设置提示信息
    public static ResponseEntity<Object> setErrorMsg(String msg) {
        BaseResponse r = new BaseResponse();
        r.setSuccess(ResultCodeEnum.FAIL.getSuccess());
        r.setCode(ResultCodeEnum.FAIL.getCode());
        r.setMessage(msg);
        return ResponseEntity.status(ResultCodeEnum.FAIL.getStatusCode()).body(r);
    }

    // 设值提示信息，并返回数据
    public static ResponseEntity<Object> setErrorMsgData(String msg, Object data) {
        BaseResponse r = new BaseResponse();
        r.setSuccess(ResultCodeEnum.FAIL.getSuccess());
        r.setCode(ResultCodeEnum.FAIL.getCode());
        r.setMessage(msg);
        if (data != null) {
            r.setData(data);
        }
        return ResponseEntity.status(ResultCodeEnum.FAIL.getStatusCode()).body(r);
    }

    // 请求成功，返回参数
    public static ResponseEntity<Object> setData(Object obj) {
        BaseResponse r = new BaseResponse();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        if (obj != null) {
            r.setData(obj);
        }
        return ResponseEntity.status(ResultCodeEnum.SUCCESS.getStatusCode()).body(r);
    }
}
