package com.pro.it.sdms.controller;


import com.pro.it.common.controller.BaseController;
import com.pro.it.sdms.entity.result.InfoAPIResult;
import com.pro.it.sdms.entity.result.ListAPIResult;
import com.pro.it.sdms.entity.vo.SemesterEvaluationVO;
import com.pro.it.sdms.service.SemesterEvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class SemesterEvaluationController extends BaseController {

    @Autowired
    private SemesterEvaluationService semesterEvaluationService;

    private class URL {
        private static final String SEMESTER_EVALUATION_ADD = "/semesterEvaluation/add";
        private static final String SEMESTER_EVALUATION_QUERY = "/semesterEvaluation/{accountNo}";
    }

    @PostMapping(URL.SEMESTER_EVALUATION_ADD)
    public InfoAPIResult<String> addClub(SemesterEvaluationVO vo) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("===> request method : [ Post ], request path [ {} ]", URL.SEMESTER_EVALUATION_ADD);
        log.info("===> request parameter {} : {} ", SemesterEvaluationVO.class.getSimpleName(), vo);
        String s = semesterEvaluationService.SemesterEvalAdd(vo);
        result.setInfo(s);
        log.info("===> response result {}", result);
        return result;
    }

    @GetMapping(URL.SEMESTER_EVALUATION_QUERY)
    public ListAPIResult<SemesterEvaluationVO> queryByAccountNo(@PathVariable("accountNo") String accountNo) {
        log.info("===> request method : [ Get ], request path [ {} ]", URL.SEMESTER_EVALUATION_QUERY);
        log.info("===> request parameter accountNo : {} ", accountNo);
        ListAPIResult<SemesterEvaluationVO> result = new ListAPIResult<>();
        List<SemesterEvaluationVO> list = semesterEvaluationService.queryByAccountNo(accountNo);
        result.setList(list);
        log.info("===> response result {}", result);
        return result;

    }

}
