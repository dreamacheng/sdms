package com.pro.it.sdms.enums;

/**
 * 申请类型枚举
 */
public enum  ApplyTypeEnum implements BaseCodeEnum {

    /**
     * 入团申请
     */
    LEAGUE_APPLY( (short) 2),

    /**
     * 入党申请
     */
    PART_APPLY( (short) 1);

    Short code;

    ApplyTypeEnum(short code) {
        this.code = code;
    }

    @Override
    public short getCode() {
        return this.code;
    }
}
