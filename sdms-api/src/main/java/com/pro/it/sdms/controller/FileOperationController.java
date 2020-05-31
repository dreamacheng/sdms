package com.pro.it.sdms.controller;

import com.pro.it.common.controller.BaseController;
import com.pro.it.sdms.controller.result.InfoAPIResult;
import com.pro.it.sdms.service.FileOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@Slf4j
public class FileOperationController extends BaseController {

    @Autowired
    private FileOperationService fileOperationService;

    private class URL {
        private static final String UPLOAD_FILE = "/file/upload";
    }


    @PostMapping(URL.UPLOAD_FILE)
    public InfoAPIResult<String> uploadFile(@RequestParam("objectType") String objectType,
                                            @RequestParam("file")MultipartFile file) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("======> request method : [ Post ], request path [ {} ]", URL.UPLOAD_FILE);
        log.info("======> request parameter Upload Type : [ {} ]", objectType);

        String filePath = fileOperationService.uploadFile(objectType, file);
        result.setInfo(filePath);

        log.info("======> response result {}", result);
        return result;

    }

}
