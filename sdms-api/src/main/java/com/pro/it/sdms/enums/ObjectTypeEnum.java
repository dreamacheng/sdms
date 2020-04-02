package com.pro.it.sdms.enums;

public enum ObjectTypeEnum implements BaseCodeEnum{

    /**
     * 社团logo
     */
    CLUB( (short) 1),

    /**
     * 头像
     */
    AVATAR( (short) 2),

    /**
     * 入团入党附件
     */
    ORGANIZATION( (short) 3);

    short code;

    ObjectTypeEnum(Short code) {
        this.code = code;
    }

    @Override
    public short getCode() {
        return this.code;
    }
}
