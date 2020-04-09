package com.pro.it.sdms.controller;

import com.pro.it.common.Constants;
import com.pro.it.common.controller.BaseController;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.common.utils.QueryResult;
import com.pro.it.common.utils.VerifyUtil;
import com.pro.it.sdms.controller.request.QueryAccountRequestEntity;
import com.pro.it.sdms.controller.request.ResetPwdRequestEntity;
import com.pro.it.sdms.controller.request.UpdatePwdRequestEntity;
import com.pro.it.sdms.controller.result.InfoAPIResult;
import com.pro.it.sdms.controller.request.PersistAccountRequestEntity;
import com.pro.it.sdms.controller.result.ListAPIResult;
import com.pro.it.sdms.entity.dto.RegisterCode;
import com.pro.it.sdms.entity.vo.AccountVO;
import com.pro.it.sdms.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    private class URL {
        private static final String REGISTER_URL = "/register";
        private static final String RESET_PASSWORD = "/account/resetPwd";
        private static final String ACCOUNT_LIST = "/account/query";
        private static final String CURRENT_ACCOUNT = "/account/current";
        private static final String LOCK_ACCOUNT = "/account/lock/{accountNo}";
        private static final String UPDATE_ACCOUNT = "/account/update";
        private static final String UPDATE_PWD = "/account/pwd";
        private static final String TEACHER_QUERY = "/account/teacher";
        private static final String AVATAR_UPDATE = "/account/avatar";
        private static final String REGISTER_CODE = "/account/code";
        private static final String RESET_PWD_CHECK = "/account/checkInfo";
    }

    @PostMapping(URL.REGISTER_URL)
    public InfoAPIResult<String> register(@RequestBody PersistAccountRequestEntity createAccountRequestEntity) throws Exception {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.REGISTER_URL);
        log.info("=== > request parameter {} : {} ", PersistAccountRequestEntity.class.getSimpleName(), createAccountRequestEntity);
        if (StringUtils.isEmpty(createAccountRequestEntity.getAccountNo())
                || StringUtils.isEmpty(createAccountRequestEntity.getUsername()) ) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "parameter require");
        }
        accountService.registerAccount(createAccountRequestEntity);
        log.info("=== > response result {}", result);
        return result;
    }

    @PostMapping(URL.RESET_PWD_CHECK)
    public InfoAPIResult<String> checkInfo(@RequestParam("identityCard") String identityCard,
                                           @RequestParam("accountNo") String accountNo,
                                           @RequestParam("username") String username,
                                           @RequestParam("tel") String tel,
                                           @RequestParam("pwd") String pwd) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.RESET_PWD_CHECK);
        String card = accountService.checkInfo(identityCard, accountNo, username, tel, pwd);
        result.setInfo(card);
        log.info("=== > response result {}", result);
        return result;
    }

    @PostMapping(URL.REGISTER_CODE)
    public InfoAPIResult<String> generateCode() {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.REGISTER_CODE);
        String code = accountService.generate();
        result.setInfo(code);
        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.REGISTER_CODE)
    public InfoAPIResult<RegisterCode> getCode() {
        InfoAPIResult<RegisterCode> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Get ], request path [ {} ]", URL.REGISTER_CODE);
        RegisterCode code = accountService.query();
        result.setInfo(code);
        log.info("=== > response result {}", result);
        return result;
    }

    @PostMapping(URL.ACCOUNT_LIST)
    public InfoAPIResult<QueryResult> queryAccounts(@RequestBody QueryAccountRequestEntity queryAccountRequestEntity) {
        InfoAPIResult<QueryResult> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.ACCOUNT_LIST);
        log.info("=== > request parameter {} : {} ", QueryAccountRequestEntity.class.getSimpleName(), queryAccountRequestEntity);
        QueryResult<AccountVO> queryResult = accountService.queryAccount(queryAccountRequestEntity);
        result.setInfo(queryResult);
        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.CURRENT_ACCOUNT)
    public InfoAPIResult<AccountVO> currentAccount() {
        InfoAPIResult<AccountVO> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Get ], request path [ {} ]", URL.CURRENT_ACCOUNT);
        AccountVO vo = accountService.currentAccount();
        result.setInfo(vo);
        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.LOCK_ACCOUNT)
    public InfoAPIResult<String> lockAccount(@PathVariable("accountNo") String accountNo) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Get ], request path [ {} ]", URL.LOCK_ACCOUNT);
        log.info("=== > request parameter {} : {} ", "AccountNo", accountNo);
        String ret = accountService.lockAccount(accountNo);
        result.setInfo(ret);
        log.info("=== > response result {}", result);
        return result;
    }

    @PostMapping(URL.UPDATE_ACCOUNT)
    public InfoAPIResult<String> updateAccount(@RequestBody PersistAccountRequestEntity param) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.UPDATE_ACCOUNT);
        log.info("=== > request parameter {} : {} ", PersistAccountRequestEntity.class.getSimpleName(), param);
        accountService.updateAccount(param);
        log.info("=== > response result {}", result);
        return result;
    }

    @PostMapping(URL.UPDATE_PWD)
    public InfoAPIResult<String> updatePwd(@RequestBody UpdatePwdRequestEntity param) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.UPDATE_ACCOUNT);
        log.info("=== > request parameter {} : {} ", UpdatePwdRequestEntity.class.getSimpleName(), param);
        accountService.updatePwd(param);
        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.TEACHER_QUERY)
    public ListAPIResult<AccountVO> teacherList() {
        ListAPIResult<AccountVO> result = new ListAPIResult<>();
        log.info("=== > request method : [ Get ]", URL.TEACHER_QUERY);
        List<AccountVO> teacherList = accountService.queryTeacher();
        result.setList(teacherList);
        log.info("=== > response result {}", result);
        return result;
    }

    @PostMapping(URL.AVATAR_UPDATE)
    public InfoAPIResult<String> avatarUpdate(@RequestParam("avatar") String avatar) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.AVATAR_UPDATE);
        log.info("=== > request parameter avatar : {} ", avatar);

        String url = accountService.updateAvatar(avatar);
        result.setInfo(url);

        log.info("=== > response result {}", result);
        return result;
    }
}
