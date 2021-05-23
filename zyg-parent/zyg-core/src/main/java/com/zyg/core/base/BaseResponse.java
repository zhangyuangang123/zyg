package com.zyg.core.base;

import org.springframework.http.ResponseEntity;

import java.util.Optional;


public class BaseResponse {
    private Boolean success;
    private Integer code;
    private String message;
    private Object data = new Object();

    /**
     * 构造器私有化
     */
    private BaseResponse() {
    }

    /**
     * POST/PUT/DELETE请求返回
     * @return
     */
    public static ResponseEntity<Object> ok() {
        BaseResponse r = new BaseResponse();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return ResponseEntity.ok(r);
    }

    /**
     * GET请求返回成功
     * @param obj
     * @return
     */
    public static ResponseEntity<Object> ok(Object obj) {
        BaseResponse r = new BaseResponse();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        r.setData(Optional.ofNullable(obj).orElse(new Object()));
        return ResponseEntity.ok(r);
    }

    /**
     * 请求错误提示
     * @param resultCodeEnum
     * @return
     */
    public static ResponseEntity<Object> error(ResultCodeEnum resultCodeEnum) {
        BaseResponse r = new BaseResponse();
        r.setSuccess(resultCodeEnum.getSuccess());
        r.setCode(resultCodeEnum.getCode());
        r.setMessage(resultCodeEnum.getMessage());
        return ResponseEntity.ok(r);
    }

    /**
     * 通用错误提示
     * @return
     */
    public static ResponseEntity<Object> error() {
        return error(ResultCodeEnum.UNKNOWN_ERROR);
    }


    public static ResponseEntity<Object> error(String msg) {
        BaseResponse r = new BaseResponse();
        r.setSuccess(false);
        r.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        r.setMessage(msg);
        return ResponseEntity.ok(r);
    }

    public static ResponseEntity<Object> error(String msg, Integer code) {
        BaseResponse r = new BaseResponse();
        r.setSuccess(false);
        r.setCode(code);
        r.setMessage(msg);
        return ResponseEntity.ok(r);
    }

    public static ResponseEntity<Object> error(String msg, Integer code, int httpCode) {
        BaseResponse r = new BaseResponse();
        r.setSuccess(false);
        r.setCode(code);
        r.setMessage(msg);
        return ResponseEntity.status(httpCode).body(r);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
