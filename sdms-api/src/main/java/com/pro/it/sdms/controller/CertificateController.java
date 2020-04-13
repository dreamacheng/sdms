package com.pro.it.sdms.controller;

import com.pro.it.sdms.controller.request.CreateCertificateRequestEntity;
import com.pro.it.sdms.controller.result.InfoAPIResult;
import com.pro.it.sdms.controller.result.ListAPIResult;
import com.pro.it.sdms.entity.vo.CertificateVO;
import com.pro.it.sdms.entity.vo.CompetitionVO;
import com.pro.it.sdms.service.CertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    private class URL {
        private static final String CERTIFICATE_ADD = "/certificate/add";
        private static final String CERTIFICATE_CURRENT = "/certificate/current/{accountNo}";
    }

    @PostMapping(URL.CERTIFICATE_ADD)
    public InfoAPIResult<String> addCertificate (@RequestBody CreateCertificateRequestEntity createCertificateRequestEntity) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.CERTIFICATE_ADD);
        log.info("=== > request parameter {} : {} ", CreateCertificateRequestEntity.class.getSimpleName(), createCertificateRequestEntity);
        String s = certificateService.certificateAdd(createCertificateRequestEntity);
        result.setInfo(s);
        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.CERTIFICATE_CURRENT)
    public ListAPIResult<CertificateVO> getCertificateList (@PathVariable("accountNo") String accountNo) {
        ListAPIResult<CertificateVO> result = new ListAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.CERTIFICATE_CURRENT);
        List<CertificateVO> s = certificateService.getCurCertificate(accountNo);
        result.setList(s);
        log.info("=== > response result {}", result);
        return result;
    }


}
