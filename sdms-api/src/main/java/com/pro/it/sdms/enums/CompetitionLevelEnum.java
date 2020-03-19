package com.pro.it.sdms.enums;

/**
 * 比赛等级枚举
 */
public enum CompetitionLevelEnum implements BaseCodeEnum {

    /**
     * 国家级
     */
    NATIONAL( (short) 1),

    /**
     * 省级
     */
    PROVINCIAL( (short) 2),

    /**
     * 市级
     */
    CITY( (short) 3),

    /**
     * 校级
     */
    SCHOOL( (short) 4),

    /**
     * 院级
     */
    COLLEGE( (short) 5);

    short code;

    CompetitionLevelEnum(Short code) {
        this.code = code;
    }

    @Override
    public short getCode() {
        return this.code;
    }
}
