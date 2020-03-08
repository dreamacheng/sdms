package com.pro.it.sdms.enums;

import lombok.Getter;

/**
 * 系统用户类型
 */
public enum IdentityEnum  implements BaseCodeEnum {

    /**
     * 管理员
     */
    MANAGER( (short) 1 ),

    /**
     * 学生
     */
    STUDENT( (short) 2 );

    Short code;


    IdentityEnum (Short code) {
        this.code = code;
    }

    @Override
    public short getCode() {
        return this.code;
    }
}
