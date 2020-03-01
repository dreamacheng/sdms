package com.pro.it.common.utils;

import com.pro.it.sdms.entity.vo.AccountVO;

import java.lang.reflect.Field;

public class VerifyUtil {

    public static boolean verifyRegisterInfo(AccountVO vo) throws IllegalAccessException {
        if (vo == null) {
            return false;
        }
        Field[] fields = vo.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(vo);
            if(value == null || "".equals(value.toString())) {
                return false;
            }
        }
        return true;
    }
}
