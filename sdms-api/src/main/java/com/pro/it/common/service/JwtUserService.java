package com.pro.it.common.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.entity.dto.Account;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JwtUserService implements UserDetailsService {

    @Autowired
    private AccountDAO accountDAO;

    private PasswordEncoder passwordEncoder;

    private static final String salt = "sdafwqertcxvbtyeguytrkgfhxcv";

    public JwtUserService() {
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public String saveUserLoginInfo(UserDetails user) throws UnsupportedEncodingException {
        // String genSalt = BCrypt.gensalt();
        //将用户登录信息存入数据库
        Account loginAccount = accountDAO.getAccountByAccountNo(user.getUsername());
        if (loginAccount == null) {
            throw new UsernameNotFoundException("user does not exist");
        }
        loginAccount.setSalt(salt);
        accountDAO.save(loginAccount);
        Algorithm algorithm = Algorithm.HMAC256(salt);
        //缓存时间一周
        Date expires = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expires)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    public UserDetails getUserLoginInfo(String accountNo) {
        //从数据库读取jwt token生成时用的salt
        Account account = accountDAO.getAccountByAccountNo(accountNo);
        if (account == null || account.getSalt() == null) {
            throw new UsernameNotFoundException("user does not exist");
        }
        String salt = account.getSalt();
        UserDetails user = loadUserByUsername(accountNo);
        //将salt放到password字段返回
        return User.builder().username(user.getUsername()).password(salt).
                authorities(user.getAuthorities()).build();
    }

    @Override
    public UserDetails loadUserByUsername(String accountNo) throws UsernameNotFoundException {
        Account account = accountDAO.getAccountByAccountNo(accountNo);
        if (account == null) {
            throw new UsernameNotFoundException("user does not exist");
        }
        return User.builder().username(account.getAccountNo())
                .password(account.getPassword())
                .roles(account.getRole()).build();
    }

    public void deleteUserLoginInfo(String accountNo) {
        //清除数据库用户登录的salt
        Account account = accountDAO.getAccountByAccountNo(accountNo);
        if (account == null) {
            throw new UsernameNotFoundException("user does not exist");
        }
        account.setSalt(StringUtils.EMPTY);
        accountDAO.save(account);
    }

}
