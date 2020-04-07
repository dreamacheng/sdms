package com.pro.sdms.ServiceTest;

import com.pro.it.SdmsApplication;
import com.pro.it.sdms.controller.response.IndexListResponseEntity;
import com.pro.it.sdms.entity.vo.IndexDataVO;
import com.pro.it.sdms.service.CatchDataService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SdmsApplication.class)
public class CatchDataTest {

    @Autowired
    private CatchDataService catchDataService;

    @Test
    public void catchDataTest() {
        catchDataService.catchData();
    }
}
