package com.pro.it.sdms.controller;

import com.pro.it.sdms.controller.response.IndexListResponseEntity;
import com.pro.it.sdms.controller.result.InfoAPIResult;
import com.pro.it.sdms.service.CatchDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @Autowired
    private CatchDataService catchDataService;

    private class URL {
        private static final String INDEX_NEWS = "/index/welcome";
    }

    @GetMapping(URL.INDEX_NEWS)
    public InfoAPIResult<IndexListResponseEntity> welcome() {
        InfoAPIResult<IndexListResponseEntity> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Get ], request path [ {} ]", URL.INDEX_NEWS);
        IndexListResponseEntity s = catchDataService.catchData();
        result.setInfo(s);
        log.info("=== > response result {}", result);
        return result;
    }
}
