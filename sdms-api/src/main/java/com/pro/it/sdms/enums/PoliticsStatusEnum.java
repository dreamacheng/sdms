package com.pro.it.sdms.enums;

public enum PoliticsStatusEnum implements BaseCodeEnum{

    /**
     * 党员
     */
    PART_MEMBER( (short) 1 ),

    /**
     * 团员
     */
    LEAGUE_MEMBER( (short) 2 ),

    /**
     * 团员
     */
    MASSES( (short) 3 );

    Short code;

    private PoliticsStatusEnum (Short code) {
        this.code = code;
    }

    @Override
    public short getCode() {
        return this.code;
    }
}
