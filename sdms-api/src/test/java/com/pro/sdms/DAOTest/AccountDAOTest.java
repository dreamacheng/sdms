package com.pro.sdms.DAOTest;

import com.pro.it.SdmsApplication;
import com.pro.it.common.service.JwtUserService;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.vo.AccountVO;
import com.pro.it.sdms.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Test
    public void addAccount() {
        AccountVO zc = new AccountVO();
        zc.setAccountNo("201513070114");
        zc.setIdentityCard("511321199610073152");
        zc.setUsername("dreamacheng");
        zc.setAge(22);
        zc.setDepartment("通信工程");
        zc.setGender((short)1);
        zc.setLodgingHouse("松林二127");
        zc.setNation("汉族");
        zc.setPassword("123456");
        zc.setPoliticsStatus((short)3);
        zc.setRole("USER");
        zc.setTel("17721882275");
        zc.setBirthday(new Date());
        accountService.registerAccount(zc);
    }
}
