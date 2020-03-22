package com.pro.it.sdms.controller;


import com.pro.it.common.controller.BaseController;
import com.pro.it.sdms.controller.result.InfoAPIResult;
import com.pro.it.sdms.controller.result.ListAPIResult;
import com.pro.it.sdms.entity.vo.SemesterEvaluationVO;
import com.pro.it.sdms.service.SemesterEvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Slf4j
public class SemesterEvaluationController extends BaseController {

    @Autowired
    private SemesterEvaluationService semesterEvaluationService;

    private class URL {
        private static final String SEMESTER_EVALUATION_EVAL = "/semesterEval/evaluate";
        private static final String SEMESTER_EVALUATION_QUERY = "/semesterEval/curAccount";
        private static final String SEMESTER_EVALUATION_ALL_QUERY = "/semesterEval/query";
        private static final String SEMESTER_EVALUATION_CUR = "/semesterEval/evaluate/{curTerm}";
    }

    @PostMapping(URL.SEMESTER_EVALUATION_EVAL)
    public InfoAPIResult<SemesterEvaluationVO> semesterEval(@RequestBody SemesterEvaluationVO vo) {
        InfoAPIResult<SemesterEvaluationVO> result = new InfoAPIResult<>();
        log.info("===> request method : [ Post ], request path [ {} ]", URL.SEMESTER_EVALUATION_EVAL);
        log.info("===> request parameter {} : {} ", SemesterEvaluationVO.class.getSimpleName(), vo);
        SemesterEvaluationVO ret = semesterEvaluationService.SemesterEval(vo);
        result.setInfo(ret);
        log.info("===> response result {}", result);
        return result;
    }

    @GetMapping(URL.SEMESTER_EVALUATION_QUERY)
    public ListAPIResult<SemesterEvaluationVO> queryByAccountNo(String accountNo) {
        log.info("===> request method : [ Get ], request path [ {} ]", URL.SEMESTER_EVALUATION_QUERY);
        log.info("===> request parameter accountNo : {} ", accountNo);
        ListAPIResult<SemesterEvaluationVO> result = new ListAPIResult<>();
        List<SemesterEvaluationVO> list = semesterEvaluationService.queryByAccountNo(accountNo);
        result.setList(list);
        log.info("===> response result {}", result);
        return result;
    }

    @GetMapping(URL.SEMESTER_EVALUATION_ALL_QUERY)
    public ListAPIResult<SemesterEvaluationVO> queryByAccountNo() {
        log.info("===> request method : [ Get ], request path [ {} ]", URL.SEMESTER_EVALUATION_ALL_QUERY);
        ListAPIResult<SemesterEvaluationVO> result = new ListAPIResult<>();
        List<SemesterEvaluationVO> list = semesterEvaluationService.query();
        result.setList(list);
        log.info("===> response result {}", result);
        return result;
    }

    @GetMapping(URL.SEMESTER_EVALUATION_CUR)
    public InfoAPIResult<SemesterEvaluationVO> semesterEvalCur(@PathVariable("curTerm") String curTerm) {
        InfoAPIResult<SemesterEvaluationVO> result = new InfoAPIResult<>();
        log.info("===> request method : [ Post ], request path [ {} ]", URL.SEMESTER_EVALUATION_CUR);
        log.info("===> request parameter current Term : {} ", curTerm);
        SemesterEvaluationVO vo = semesterEvaluationService.queryCurrent(curTerm);
        result.setInfo(vo);
        log.info("===> response result {}", result);
        return result;
    }

}
