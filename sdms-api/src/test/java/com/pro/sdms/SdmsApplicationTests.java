package com.pro.sdms;

import com.pro.it.sdms.entity.vo.AccountVO;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.SemesterEnum;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=SdmsApplicationTests.class)
class SdmsApplicationTests {


    @Test
    void contextLoads() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        System.out.println(encoder.encode("e10adc3949ba59abbe56e057f20f883e"));
    }

    @Test
    void testDate() {
        LocalDate localDate = LocalDate.now();
        LocalDate previous = LocalDate.of(2018, 9 , 7);
        Period period = Period.between(previous, localDate);
        short currentTerm = (short) ((period.getYears()* 12 + period.getMonths())/6 + 1);
        System.out.printf("目标日期距离今天的时间差：%d 个月", (period.getYears()* 12 + period.getMonths())/6);
        System.out.println(String.format("当前学期为 ： %s", BaseCodeEnum.codeOf(SemesterEnum.class, currentTerm)));
    }

    @Test
    void testTerm() throws ParseException {
        AccountVO  vo = new AccountVO();
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate1 = dateFormat1.parse("2019-09-01");
        vo.setEnrollment(myDate1);
        System.out.println(vo.getCurrentTerm());
    }


}
