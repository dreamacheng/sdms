package com.pro.it.sdms.enums;

public enum GenderEnum implements BaseCodeEnum{

    /**
     * 男
     */
    MALE( (short) 1 ),

    /**
     * 女
     */
    FEMALE( (short) 2 );

    Short code;


    GenderEnum (Short code) {
        this.code = code;
    }

    @Override
    public short getCode() {
        return this.code;
    }
}
