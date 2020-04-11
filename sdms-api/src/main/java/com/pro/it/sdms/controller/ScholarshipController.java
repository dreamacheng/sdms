package com.pro.it.sdms.controller;

import com.pro.it.common.controller.BaseController;
import com.pro.it.sdms.controller.response.ScholarshipListResponseEntity;
import com.pro.it.sdms.controller.result.InfoAPIResult;
import com.pro.it.sdms.controller.result.ListAPIResult;
import com.pro.it.sdms.entity.dto.Scholarship;
import com.pro.it.sdms.entity.vo.ScholarshipVO;
import com.pro.it.sdms.service.ScholarshipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Slf4j
public class ScholarshipController extends BaseController {

    @Autowired
    private ScholarshipService scholarshipService;

    private class URL {
        private static final String CURRENT_TERM = "/scholarship/query/{curTerm}";
        private static final String QUERY_CURRENT_STUDENT = "/scholarship/query";
        private static final String QUERY_CURRENT_STUDENT_PASS = "/scholarship/query/pass";
        private static final String SCHOLARSHIP_ADD = "/scholarship/add";
        private static final String SCHOLARSHIP_QUERY = "/scholarship/queryAll";
        private static final String SCHOLARSHIP_APPROVAL = "/scholarship/approval";
    }

    @PostMapping(URL.SCHOLARSHIP_ADD)
    public InfoAPIResult<BigDecimal> competitionAdd(@RequestBody ScholarshipVO vo) {
        InfoAPIResult<BigDecimal> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.SCHOLARSHIP_ADD);
        log.info("=== > request parameter {} : {} ", ScholarshipVO.class.getSimpleName(), vo);
        BigDecimal s = scholarshipService.addApply(vo);
        result.setInfo(s);
        log.info("=== > response result {}", result);
        return result;
    }

    @PostMapping(URL.SCHOLARSHIP_APPROVAL)
    public InfoAPIResult<BigDecimal> approval(@RequestBody ScholarshipVO vo) {
        InfoAPIResult<BigDecimal> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.SCHOLARSHIP_APPROVAL);
        log.info("=== > request parameter {} : {} ", ScholarshipVO.class.getSimpleName(), vo);
        BigDecimal s = scholarshipService.approval(vo);
        result.setInfo(s);
        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.CURRENT_TERM)
    public InfoAPIResult<ScholarshipVO> semesterEvalCur(@PathVariable("curTerm") String curTerm) {
        InfoAPIResult<ScholarshipVO> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.CURRENT_TERM);
        log.info("=== > request parameter current Term : {} ", curTerm);
        ScholarshipVO vo = scholarshipService.getCurrentTerm(curTerm);
        result.setInfo(vo);
        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.QUERY_CURRENT_STUDENT)
    public ListAPIResult<ScholarshipVO> queryByAccountNo() {
        log.info("=== > request method : [ Get ], request path [ {} ]", URL.QUERY_CURRENT_STUDENT);
        ListAPIResult<ScholarshipVO> result = new ListAPIResult<>();
        List<ScholarshipVO> list = scholarshipService.query();
        result.setList(list);
        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.QUERY_CURRENT_STUDENT_PASS)
    public ListAPIResult<ScholarshipVO> queryByAccountNoPass() {
        log.info("=== > request method : [ Get ], request path [ {} ]", URL.QUERY_CURRENT_STUDENT_PASS);
        ListAPIResult<ScholarshipVO> result = new ListAPIResult<>();
        List<ScholarshipVO> list = scholarshipService.queryPass();
        result.setList(list);
        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.SCHOLARSHIP_QUERY)
    public InfoAPIResult<ScholarshipListResponseEntity> queryAll() {
        log.info("=== > request method : [ Get ], request path [ {} ]", URL.SCHOLARSHIP_QUERY);
        InfoAPIResult<ScholarshipListResponseEntity> result = new InfoAPIResult<>();
        ScholarshipListResponseEntity info = scholarshipService.queryAll();
        result.setInfo(info);
        log.info("=== > response result {}", result);
        return result;
    }

}
