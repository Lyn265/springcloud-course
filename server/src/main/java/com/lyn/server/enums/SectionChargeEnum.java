package com.lyn.server.enums;

public enum SectionChargeEnum {

    CHARGE("C","收费"),
    FREE("F","免费"),

    ;

    SectionChargeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
