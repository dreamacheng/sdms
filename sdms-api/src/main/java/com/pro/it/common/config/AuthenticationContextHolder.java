package com.pro.it.common.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;

public class AuthenticationContextHolder {

    private static final ThreadLocal<String> context = new ThreadLocal<>();

    public static void reset() {
        context.remove();
    }

    public static void setUsername(String token) {
        Claim username = JWT.decode(token).getClaim("username");
        context.set(username.asString());
    }

    public static String getUsername() {
        return context.get();
    }
}
