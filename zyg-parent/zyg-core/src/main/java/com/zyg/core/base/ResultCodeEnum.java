package com.zyg.core.base;

import lombok.Getter;


@Getter
public enum ResultCodeEnum {
    /**
     * 成功
     */
    SUCCESS(true,20000,"成功"),
    /**
     * 未知错误
     */
    UNKNOWN_ERROR(false,20001,"未知错误"),
    /**
     * 参数错误
     */
    PARAM_ERROR(false,20002,"参数错误"),
    /**
     * token错误
     */
    TOKEN_ERROR(false,401,"token错误"),

    /**
     * 管理端： 不能删除未停用的数据
     */
    NOT_DELETE_ENABLE(false,402,"未停用，不能删除！"),
    /**
     * 商品券无库存!
     */
    NO_COUPON_STORE(false,506,"商品券无库存!"),
    /**
     * 管理端： 规则已使用，不能删除
     */
    NO_DELETE_BARGAIN_ACTIVITY(false,403,"规则已被使用，不能删除!"),
    /**
     * 管理端： 规则已使用，不能删除
     */
    NO_ENABLE_BARGAIN_ACTIVITY(false,404,"规则已被使用，不能停用!");

    /**
     * 响应是否成功
     */
    private Boolean success;
    /**
     * 响应是否成功
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String message;

    ResultCodeEnum(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

}
