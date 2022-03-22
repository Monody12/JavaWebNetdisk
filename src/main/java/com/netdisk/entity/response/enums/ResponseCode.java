package com.netdisk.entity.response.enums;

/**
 * 通用返回值类型枚举
 */
public enum ResponseCode {

    /**
     * 几种状态
     */
    Success(200, "成功"),
    Fail(500, "操作失败：服务器内部错误"),
    InvalidParam(-1, "参数非法"),
    PasswordError(403, "身份验证错误：密码错误"),
    TokenInvalid(401, "身份验证错误：无效的Token"),
    AccessDenied(401, "身份验证错误：请进行安全验证"),
    UserNotFound(404, "操作失败：用户不存在"),
    FileNotFound(404, "操作失败：文件不存在");

    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
