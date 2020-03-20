package com.pro.it.sdms.controller;

import com.pro.it.common.controller.BaseController;
import com.pro.it.sdms.controller.result.InfoAPIResult;
import com.pro.it.sdms.entity.vo.CompetitionVO;
import com.pro.it.sdms.service.CompetitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CompetitionController extends BaseController {

    @Autowired
    private CompetitionService competitionService;

    private class URL {
        private static final String COMPETITION_ADD = "/competition/add";
    }

    /**
     * 添加比赛
     * @param vo
     * @return
     */
    @PostMapping(URL.COMPETITION_ADD)
    public InfoAPIResult<String> competitionAdd(CompetitionVO vo) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("===> request method : [ Post ], request path [ {} ]", URL.COMPETITION_ADD);
        log.info("===> request parameter {} : {} ", CompetitionVO.class.getSimpleName(), vo);
        String s = competitionService.competitionAdd(vo);
        result.setInfo(s);
        log.info("===> response result {}", result);
        return result;
    }
}
