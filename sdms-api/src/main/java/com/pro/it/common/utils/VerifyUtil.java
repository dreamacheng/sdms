package com.pro.it.common.utils;

import com.pro.it.sdms.controller.request.PersistAccountRequestEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;
import java.util.Collection;

public class VerifyUtil {

    public static boolean verifyRegisterInfo(PersistAccountRequestEntity vo) throws IllegalAccessException {
        if (vo == null) {
            return false;
        }
        Field[] fields = vo.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(vo);

            if(value == null || "".equals(value.toString())) {
                if ("registerCode".equals(field.getName())) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    public static String getCurrentRole() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String result = "";
        for (GrantedAuthority authority : authorities) {
            result = authority.getAuthority();
        }
        return result;
    }
}
