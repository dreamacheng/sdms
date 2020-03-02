package com.pro.it.common.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.pro.it.common.service.JwtUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtRefreshSuccessHandler implements AuthenticationSuccessHandler {

    // token刷新间隔 5 min
    private static final int TOKEN_REFRESH_INTERVAL = 300;

    private JwtUserService jwtUserService;

    public JwtRefreshSuccessHandler(JwtUserService jwtUserService) {
        this.jwtUserService = jwtUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        DecodedJWT jwt = ((JwtAuthenticationToken)authentication).getToken();
        boolean shouldRefresh = shouldTokenRefresh(jwt.getIssuedAt());
        if (shouldRefresh) {
            String newToken = jwtUserService.saveUserLoginInfo((UserDetails) authentication.getPrincipal());
            response.setHeader("Authorization", newToken);
        }
    }

    private boolean shouldTokenRefresh(Date issuedAt) {
        LocalDateTime issueTime = LocalDateTime.ofInstant(issuedAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(TOKEN_REFRESH_INTERVAL).isAfter(issueTime);
    }

}
