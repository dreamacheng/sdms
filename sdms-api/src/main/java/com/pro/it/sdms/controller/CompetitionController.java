package com.pro.it.sdms.controller;

import com.pro.it.common.controller.BaseController;
import com.pro.it.sdms.controller.result.InfoAPIResult;
import com.pro.it.sdms.controller.result.ListAPIResult;
import com.pro.it.sdms.entity.vo.CompetitionVO;
import com.pro.it.sdms.service.CompetitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Slf4j
public class CompetitionController extends BaseController {

    @Autowired
    private CompetitionService competitionService;

    private class URL {
        private static final String COMPETITION_ADD = "/competition/add";
        private static final String COMPETITION_List = "/competition/list";
        private static final String OMPETITION_GET = "/competition/{id}";
    }

    /**
     * 添加比赛
     * @param vo
     * @return
     */
    @PostMapping(URL.COMPETITION_ADD)
    public InfoAPIResult<String> competitionAdd(@RequestBody CompetitionVO vo) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("===> request method : [ Post ], request path [ {} ]", URL.COMPETITION_ADD);
        log.info("===> request parameter {} : {} ", CompetitionVO.class.getSimpleName(), vo);
        String s = competitionService.competitionAdd(vo);
        result.setInfo(s);
        log.info("===> response result {}", result);
        return result;
    }

    @GetMapping(URL.COMPETITION_List)
    public ListAPIResult<CompetitionVO> queryList() {
        ListAPIResult<CompetitionVO> result = new ListAPIResult<>();
        log.info("===> request method : [ GET ], request path [ {} ]", URL.COMPETITION_List);
        List<CompetitionVO> list = competitionService.queryAll();
        result.setList(list);
        log.info("===> response result {}", result);
        return result;
    }

    @GetMapping(URL.OMPETITION_GET)
    public InfoAPIResult<CompetitionVO> query(@PathVariable("id") BigDecimal id) {
        InfoAPIResult<CompetitionVO> result = new InfoAPIResult<>();
        log.info("===> request method : [ GET ], request path [ {} ]", URL.OMPETITION_GET);
        log.info("===> request parameter id : {} ", id);
        CompetitionVO vo = competitionService.query(id);
        result.setInfo(vo);
        log.info("===> response result {}", result);
        return result;
    }
}
