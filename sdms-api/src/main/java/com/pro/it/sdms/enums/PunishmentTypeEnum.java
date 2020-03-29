package com.pro.it.sdms.enums;

public enum  PunishmentTypeEnum implements BaseCodeEnum {

    /**
     * 警告
     */
    WARNING( (short) 1),

    /**
     * 记过
     */
    FAULT_1( (short) 2),

    /**
     * 记大过
     */
    FAULT_2( (short) 3),

    /**
     * 留校查看
     */
    SCHOOL_VIEW( (short) 4),

    /**
     * 开除学籍
     */
    EXIT( (short) 5);

    short code;

    PunishmentTypeEnum(Short code) {
        this.code = code;
    }

    @Override
    public short getCode() {
        return this.code;
    }
}
