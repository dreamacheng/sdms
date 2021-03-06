package com.pro.it.sdms.controller;


import com.pro.it.common.controller.BaseController;
import com.pro.it.sdms.controller.result.InfoAPIResult;
import com.pro.it.sdms.controller.result.ListAPIResult;
import com.pro.it.sdms.entity.vo.PunishmentVO;
import com.pro.it.sdms.service.PunishmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 处分相关controller
 */
@RestController
@Slf4j
public class PunishmentController extends BaseController {

    @Autowired
    private PunishmentService punishmentService;

    private class URL {
        private static final String PUNISHMENT_ADD = "/punishment/add";
        private static final String PUNISHMENT_CANCEL = "/punishment/cancel/{id}";
        private static final String PUNISHMENT_QUERY = "/punishment/query";
        private static final String PUNISHMENT_ALL = "/punishment/all";
    }

    @GetMapping(URL.PUNISHMENT_QUERY)
    public ListAPIResult<PunishmentVO> getCurAccountAll() {
        ListAPIResult<PunishmentVO> result = new ListAPIResult<>();
        log.info("======> request method : [ GET ], request path [ {} ]", URL.PUNISHMENT_QUERY);
        List<PunishmentVO> list = punishmentService.queryPunishmentCur();
        result.setList(list);
        log.info("======> response result {}", result);
        return result;
    }

    /**
     * 处分某人
     * @param vo
     * @return
     */
    @PostMapping(URL.PUNISHMENT_ADD)
    public InfoAPIResult<BigDecimal> addPunishment(@RequestBody PunishmentVO vo) {
        InfoAPIResult<BigDecimal> result = new InfoAPIResult<>();
        log.info("======> request method : [ Post ], request path [ {} ]", URL.PUNISHMENT_ADD);
        log.info("======> request parameter {} : {} ", PunishmentVO.class.getSimpleName(), vo);
        BigDecimal s = punishmentService.addPunishment(vo);
        result.setInfo(s);
        log.info("======> response result {}", result);
        return result;
    }

    /**
     * 取消处分
     * @param punishmentId 处分id
     * @return
     */
    @GetMapping(URL.PUNISHMENT_CANCEL)
    public InfoAPIResult<BigDecimal> cancelPunishment(@PathVariable("id")BigDecimal punishmentId) {

        InfoAPIResult<BigDecimal> result = new InfoAPIResult<>();
        log.info("======> request method : [ Post ], request path [ {} ]", URL.PUNISHMENT_ADD);
        log.info("======> request parameter punishment_id : {} ", punishmentId);
        BigDecimal s = punishmentService.cancelPunishment(punishmentId);
        result.setInfo(s);
        log.info("======> response result {}", result);
        return result;
    }

    @GetMapping(URL.PUNISHMENT_ALL)
    public ListAPIResult<PunishmentVO> queryAll(@RequestParam("accountNo") String accountNo,
                                                @RequestParam("username") String username,
                                                @RequestParam("type") String type) {
        ListAPIResult<PunishmentVO> result = new ListAPIResult<>();
        log.info("======> request method : [ GET ], request path [ {} ]", URL.PUNISHMENT_ALL);
        List<PunishmentVO> list = punishmentService.queryAll(accountNo, username, type);
        result.setList(list);
        log.info("======> response result {}", result);
        return result;
    }


}
