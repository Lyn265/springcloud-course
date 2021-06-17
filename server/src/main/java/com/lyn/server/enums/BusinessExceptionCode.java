package com.lyn.server.enums;

public enum BusinessExceptionCode {
    USER_LOGIN_NAME_EXIST("用户名已存在"),
    USER_LOGIN_NAME_NOT_EXIST("用户名或密码不存在"),
    ;

    BusinessExceptionCode(String desc) {
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
