package com.pro.it.sdms.controller;


import com.pro.it.common.controller.BaseController;
import com.pro.it.sdms.controller.result.InfoAPIResult;
import com.pro.it.sdms.entity.vo.PunishmentVO;
import com.pro.it.sdms.service.PunishmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

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
    }


    /**
     * 处分某人
     * @param vo
     * @return
     */
    @PostMapping(URL.PUNISHMENT_ADD)
    public InfoAPIResult<String> addPunishment(PunishmentVO vo) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("===> request method : [ Post ], request path [ {} ]", URL.PUNISHMENT_ADD);
        log.info("===> request parameter {} : {} ", PunishmentVO.class.getSimpleName(), vo);
        String s = punishmentService.addPunishment(vo);
        result.setInfo(s);
        log.info("===> response result {}", result);
        return result;
    }

    /**
     * 取消处分
     * @param punishmentId 处分id
     * @return
     */
    @GetMapping(URL.PUNISHMENT_CANCEL)
    public InfoAPIResult cancelPunishment(@PathVariable()BigDecimal punishmentId) {

        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("===> request method : [ Post ], request path [ {} ]", URL.PUNISHMENT_ADD);
        log.info("===> request parameter punishment_id : {} ", punishmentId);
        String s = punishmentService.cancelPunishment(punishmentId);
        result.setInfo(s);
        log.info("===> response result {}", result);
        return result;
    }


}
