package com.pro.it.sdms.service;

import com.pro.it.common.utils.QueryResult;
import com.pro.it.sdms.controller.request.QueryAccountRequestEntity;
import com.pro.it.sdms.controller.request.ResetPwdRequestEntity;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.controller.request.CreateAccountRequestEntity;
import com.pro.it.sdms.entity.vo.AccountVO;

public interface AccountService {

    void registerAccount(CreateAccountRequestEntity vo);

    void resetPwd(ResetPwdRequestEntity resetPwdRequestEntity);

    QueryResult<AccountVO> queryAccount(QueryAccountRequestEntity queryAccountRequestEntity);

    AccountVO currentAccount();
}
