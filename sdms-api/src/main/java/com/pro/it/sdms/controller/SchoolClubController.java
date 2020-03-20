package com.pro.it.sdms.controller;

import com.pro.it.common.controller.BaseController;
import com.pro.it.sdms.controller.result.InfoAPIResult;
import com.pro.it.sdms.entity.vo.SchoolClubVO;
import com.pro.it.sdms.service.SchoolClubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SchoolClubController extends BaseController {

    @Autowired
    private SchoolClubService schoolClubService;

    private class URL {
        private static final String CLUB_ADD = "/club/add";
    }

    @PostMapping(URL.CLUB_ADD)
    public InfoAPIResult<String> addClub(SchoolClubVO vo) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("===> request method : [ Post ], request path [ {} ]", URL.CLUB_ADD);
        log.info("===> request parameter {} : {} ", SchoolClubVO.class.getSimpleName(), vo);
        String s = schoolClubService.addClub(vo);
        result.setInfo(s);
        log.info("===> response result {}", result);
        return result;
    }
}
