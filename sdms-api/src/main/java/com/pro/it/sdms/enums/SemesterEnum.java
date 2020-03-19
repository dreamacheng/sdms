package com.pro.it.sdms.enums;

/**
 * 学期枚举
 */
public enum SemesterEnum implements BaseCodeEnum {

    /**
     * 大一上
     */
    CLASS_1( (short) 1),

    /**
     * 大一下
     */
    CLASS_2( (short) 2),

    /**
     * 大二上
     */
    CLASS_3( (short) 3),

    /**
     * 大二下
     */
    CLASS_4( (short) 4),

    /**
     * 大三上
     */
    CLASS_5( (short) 5),

    /**
     * 大三下
     */
    CLASS_6( (short) 6),

    /**
     * 大四上
     */
    CLASS_7( (short) 7),

    /**
     * 大四下
     */
    CLASS_8( (short) 8);

    Short code;

    SemesterEnum(Short code) {
        this.code = code;
    }

    @Override
    public short getCode() {
        return this.code;
    }
}
