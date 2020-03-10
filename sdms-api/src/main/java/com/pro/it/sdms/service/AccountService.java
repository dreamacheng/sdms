package com.pro.it.sdms.service;

import com.pro.it.common.utils.QueryResult;
import com.pro.it.sdms.controller.request.QueryAccountRequestEntity;
import com.pro.it.sdms.controller.request.ResetPwdRequestEntity;
import com.pro.it.sdms.controller.request.PersistAccountRequestEntity;
import com.pro.it.sdms.controller.request.UpdatePwdRequestEntity;
import com.pro.it.sdms.entity.vo.AccountVO;

public interface AccountService {

    void registerAccount(PersistAccountRequestEntity vo);

    void resetPwd(ResetPwdRequestEntity resetPwdRequestEntity);

    QueryResult<AccountVO> queryAccount(QueryAccountRequestEntity queryAccountRequestEntity);

    AccountVO currentAccount();

    String lockAccount(String accountNo);

    void updateAccount(PersistAccountRequestEntity param);

    void updatePwd(UpdatePwdRequestEntity param);
}
