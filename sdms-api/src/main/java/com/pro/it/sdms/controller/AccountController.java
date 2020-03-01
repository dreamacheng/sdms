package com.pro.it.sdms.controller;

import com.pro.it.common.Constants;
import com.pro.it.common.utils.VerifyUtil;
import com.pro.it.sdms.entity.APIResult;
import com.pro.it.sdms.entity.vo.AccountVO;
import com.pro.it.sdms.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    private class URL {
        private static final String REGISTER_URL = "/sdms/register";
    }

    @PostMapping(URL.REGISTER_URL)
    public APIResult<String> register(AccountVO vo) {
        APIResult<String> result = new APIResult<>();
        log.info("===> request method : Post, request path {}", URL.REGISTER_URL);
        log.info("===> request parameter {} : {} ", AccountVO.class.getSimpleName(), vo);
        try {
            if (!VerifyUtil.verifyRegisterInfo(vo)) {
                throw new Exception("parameter missing");
            }
            accountService.registerAccount(vo);
        } catch (Exception e) {
            result.setCode(Constants.Code.PARAM_REQUIRED);
            result.setErrMsg(e.getMessage());
        }
        log.info("===> response result {}", result);
        return result;
    }
}
