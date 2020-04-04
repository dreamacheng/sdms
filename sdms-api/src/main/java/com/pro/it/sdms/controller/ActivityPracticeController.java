package com.pro.it.sdms.controller;

import com.pro.it.common.controller.BaseController;
import com.pro.it.sdms.controller.result.InfoAPIResult;
import com.pro.it.sdms.controller.result.ListAPIResult;
import com.pro.it.sdms.entity.vo.ActivityPracticeVO;
import com.pro.it.sdms.entity.vo.ActivityResultVO;
import com.pro.it.sdms.service.ActivityPracticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Slf4j
public class ActivityPracticeController extends BaseController {

    @Autowired
    private ActivityPracticeService activityPracticeService;

    private class URL {
        private static final String ACTIVITY_ADD = "/activity/add";
        private static final String ACTIVITY_QUERY = "/activity/query";
        private static final String ACTIVITY_QUERY_DETAIL = "/activity/detail/{id}";
        private static final String ACTIVITY_JOIN = "/activity/join";
        private static final String ACTIVITY_JOINED = "/activity/joined";
        private static final String ACTIVITY_SUMMARY = "/activity/summary";
    }

    /**
     * 添加活动
     * @param vo
     * @return
     */
    @PostMapping(URL.ACTIVITY_ADD)
    public InfoAPIResult<String> activityAdd(@RequestBody ActivityPracticeVO vo) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.ACTIVITY_ADD);
        log.info("=== > request parameter {} : {} ", ActivityPracticeVO.class.getSimpleName(), vo);

        String s = activityPracticeService.activityAdd(vo);
        result.setInfo(s);

        log.info("=== > response result {}", result);
        return result;
    }


    @GetMapping(URL.ACTIVITY_QUERY)
    public ListAPIResult<ActivityPracticeVO> queryList() {
        ListAPIResult<ActivityPracticeVO> result = new ListAPIResult<>();
        log.info("=== > request method : [ GET ], request path [ {} ]", URL.ACTIVITY_QUERY);

        List<ActivityPracticeVO> list = activityPracticeService.queryAll();
        result.setList(list);

        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.ACTIVITY_QUERY_DETAIL)
    public InfoAPIResult<ActivityPracticeVO> query(@PathVariable("id") BigDecimal id) {
        InfoAPIResult<ActivityPracticeVO> result = new InfoAPIResult<>();
        log.info("=== > request method : [ GET ], request path [ {} ]", URL.ACTIVITY_QUERY_DETAIL);
        log.info("=== > request parameter id : {} ", id);
        ActivityPracticeVO vo = activityPracticeService.query(id);
        result.setInfo(vo);
        log.info("=== > response result {}", result);
        return result;
    }

    @PostMapping(URL.ACTIVITY_JOIN)
    public InfoAPIResult<String> join(@RequestParam("activityId") BigDecimal activityId) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("=== > request method : [ POST ], request path [ {} ]", URL.ACTIVITY_JOIN);
        log.info("=== > request parameter activityId : {} ", activityId);
        String s = activityPracticeService.join(activityId);
        result.setInfo(s);
        log.info("=== > response result {}", result);
        return result;
    }

    @PostMapping(URL.ACTIVITY_SUMMARY)
    public InfoAPIResult<String> summary(@RequestParam("activityResultId") BigDecimal activityResultId,
                                         @RequestParam("summary") String summary) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("=== > request method : [ POST ], request path [ {} ]", URL.ACTIVITY_SUMMARY);
        log.info("=== > request parameter activityResultId : {} ", activityResultId);

        String s = activityPracticeService.summary(activityResultId, summary);
        result.setInfo(s);

        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.ACTIVITY_JOINED)
    public ListAPIResult<ActivityResultVO> queryJoin() {
        ListAPIResult<ActivityResultVO> result = new ListAPIResult<>();
        log.info("=== > request method : [ POST ], request path [ {} ]", URL.ACTIVITY_JOINED);

        List<ActivityResultVO> list = activityPracticeService.queryJoin();
        result.setList(list);

        log.info("=== > response result {}", result);
        return result;
    }
}
