package com.pro.it.sdms.enums;


public interface BaseCodeEnum {

    /**
     * 获取Code值
     * @return Code值
     */
    short getCode();

    /**
     * 将数值转换为枚举类
     *
     * @param clazz 枚举类型
     * @param code  值
     * @param <E>   枚举
     * @return  枚举
     */
    public static <E extends Enum<?> & BaseCodeEnum> E codeOf(Class<E> clazz, short code){
        final E[] enumConstants = clazz.getEnumConstants();
        for(E e : enumConstants){
            if(e.getCode() == code){
                return e;
            }
        }

        final String msg = String.format("Cannot convert %d to %s by code value", code, clazz.getSimpleName());
        throw new IllegalArgumentException(msg);
    }


}
