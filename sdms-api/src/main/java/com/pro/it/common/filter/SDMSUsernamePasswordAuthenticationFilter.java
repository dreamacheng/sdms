package com.pro.it.common.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

public class SDMSUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public SDMSUsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login","POST"));
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(getAuthenticationManager(), "authenticationManager must be specified");
        Assert.notNull(getSuccessHandler(), "AuthenticationSuccessHandler must be specified");
        Assert.notNull(getFailureHandler(), "AuthenticationFailureHandler must be specified");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        String body = StreamUtils.copyToString(httpServletRequest.getInputStream(), Charset.forName("UTF-8"));
        String accountNo = null, password = null;

        if (StringUtils.hasText(body)) {
            JSONObject jsonObject = JSON.parseObject(body);
            accountNo = jsonObject.getString("accountNo");
            password = jsonObject.getString("password");
        }

        if (accountNo == null) {
            accountNo = "";
        }
        if (password == null) {
            password = "";
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(accountNo, password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
