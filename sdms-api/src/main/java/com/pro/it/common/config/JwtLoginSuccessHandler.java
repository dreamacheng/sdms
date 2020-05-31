package com.pro.it.common.config;

import com.alibaba.fastjson.JSONObject;
import com.pro.it.common.service.JwtUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.security.auth.login.AccountLockedException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {

    private JwtUserService jwtUserService;

    public JwtLoginSuccessHandler(JwtUserService jwtUserService) {
        this.jwtUserService = jwtUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String token = null;
        try {
            token = jwtUserService.saveUserLoginInfo((UserDetails) authentication.getPrincipal());
            log.info("======> Return token : [ {} ]", token);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            response.getWriter().write(jsonObject.toString());
        } catch (AccountLockedException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }

    }
}
