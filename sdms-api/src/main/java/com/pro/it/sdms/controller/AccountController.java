package com.pro.it.sdms.controller;

import com.pro.it.common.Constants;
import com.pro.it.common.controller.BaseController;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.common.utils.VerifyUtil;
import com.pro.it.sdms.controller.request.ResetPwdRequestEntity;
import com.pro.it.sdms.entity.result.InfoAPIResult;
import com.pro.it.sdms.entity.vo.AccountVO;
import com.pro.it.sdms.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    private class URL {
        private static final String REGISTER_URL = "/register";
        private static final String RESET_PASSWORD = "/account/resetPwd";
    }

    @PostMapping(URL.REGISTER_URL)
    public InfoAPIResult<String> register(@RequestBody AccountVO vo) throws Exception {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("===> request method : [ Post ], request path [ {} ]", URL.REGISTER_URL);
        log.info("===> request parameter {} : {} ", AccountVO.class.getSimpleName(), vo);
        if (!VerifyUtil.verifyRegisterInfo(vo)) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "parameter require");
        }
        accountService.registerAccount(vo);
        log.info("===> response result {}", result);
        return result;
    }

    @PostMapping(URL.RESET_PASSWORD)
    public InfoAPIResult<String> resetPwd(ResetPwdRequestEntity resetPwdRequestEntity) throws Exception {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("===> request method : [ Post ], request path [ {} ]", URL.RESET_PASSWORD);
        log.info("===> request parameter {} : {} ", ResetPwdRequestEntity.class.getSimpleName(), resetPwdRequestEntity);
        if (resetPwdRequestEntity == null || StringUtils.isEmpty(resetPwdRequestEntity.getAccountNo())
            || StringUtils.isEmpty(resetPwdRequestEntity.getIdentityCard())  || StringUtils.isEmpty(resetPwdRequestEntity.getUsername())) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED,"parameter require");
        }
        accountService.resetPwd(resetPwdRequestEntity);
        log.info("===> response result {}", result);
        return result;
    }
}
