package com.pro.sdms.DAOTest;

import com.pro.it.SdmsApplication;
import com.pro.it.common.service.JwtUserService;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SdmsApplication.class)
public class AccountDAOTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUserService jwtUserService;

    @Test
    public void testExist() {
        Account accountByAccountNo = accountService.getUserByNo("201513070114");
        System.out.println(accountByAccountNo);;
        UserDetails userDetails = jwtUserService.loadUserByUsername("201513070114");
        System.out.println(userDetails);
    }
}
