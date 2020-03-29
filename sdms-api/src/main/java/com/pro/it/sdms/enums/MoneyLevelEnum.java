package com.pro.it.sdms.enums;

public enum MoneyLevelEnum implements BaseCodeEnum {
    /**
     * 国家奖学金
     */
    COUNTRY_1( (short) 1),

    /**
     * 国家励志奖学金
     */
    COUNTRY_2( (short) 2),

    /**
     * 校级一等奖
     */
    SCHOOL_1( (short) 3),

    /**
     * 校级二等奖
     */
    SCHOOL_2( (short) 4),

    /**
     * 校级三等奖
     */
    SCHOOL_3( (short) 5);

    short code;

    MoneyLevelEnum(Short code) {
        this.code = code;
    }

    @Override
    public short getCode() {
        return this.code;
    }
}
