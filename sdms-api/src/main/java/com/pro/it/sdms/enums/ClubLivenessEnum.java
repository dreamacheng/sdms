package com.pro.it.sdms.enums;

public enum  ClubLivenessEnum implements BaseCodeEnum{

    /**
     * 不活跃
     */
    LEVEL_1( (short) 1),

    /**
     * 一般活跃
     */
    LEVEL_2( (short) 2),

    /**
     * 较活跃
     */
    LEVEL_3( (short) 3);


    short code;

    ClubLivenessEnum(Short code) {
        this.code = code;
    }

    @Override
    public short getCode() {
        return this.code;
    }
}
