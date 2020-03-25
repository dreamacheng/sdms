package com.pro.it.sdms.controller;

import com.pro.it.common.controller.BaseController;
import com.pro.it.sdms.controller.result.InfoAPIResult;
import com.pro.it.sdms.controller.result.ListAPIResult;
import com.pro.it.sdms.controller.request.CompetitionResultRequestEntity;
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
        private static final String COMPETITION_WINNER_ADD = "/competition/winner/add";
        private static final String COMPETITION_JOIN = "/competition/join";
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

    @PostMapping(URL.COMPETITION_JOIN)
    public InfoAPIResult<String> join(@RequestParam("accountNo") String accountNo,
                                      @RequestParam("competitionId") BigDecimal competitionId) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("===> request method : [ POST ], request path [ {} ]", URL.COMPETITION_JOIN);
        log.info("===> request parameter accountNo : {}, competitionId : {} ", accountNo, competitionId);
        String s = competitionService.join(accountNo, competitionId);
        result.setInfo(s);
        log.info("===> response result {}", result);
        return result;
    }

    /**
     * 添加比赛获奖者
     * @param vo
     * @return
     */
    @PostMapping(URL.COMPETITION_WINNER_ADD)
    public InfoAPIResult<String> winnerAdd(@RequestBody CompetitionResultRequestEntity vo) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("===> request method : [ Post ], request path [ {} ]", URL.COMPETITION_ADD);
        log.info("===> request parameter {} : {} ", CompetitionResultRequestEntity.class.getSimpleName(), vo);
        String s = competitionService.winnerAdd(vo);
        result.setInfo(s);
        log.info("===> response result {}", result);
        return result;
    }
}
