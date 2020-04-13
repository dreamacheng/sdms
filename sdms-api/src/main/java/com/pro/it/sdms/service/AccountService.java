package com.pro.it.sdms.service;

import com.pro.it.common.utils.QueryResult;
import com.pro.it.sdms.controller.request.QueryAccountRequestEntity;
import com.pro.it.sdms.controller.request.ResetPwdRequestEntity;
import com.pro.it.sdms.controller.request.PersistAccountRequestEntity;
import com.pro.it.sdms.controller.request.UpdatePwdRequestEntity;
import com.pro.it.sdms.entity.dto.RegisterCode;
import com.pro.it.sdms.entity.vo.AccountVO;

import java.util.List;

public interface AccountService {

    void registerAccount(PersistAccountRequestEntity vo);

    QueryResult<AccountVO> queryAccount(QueryAccountRequestEntity queryAccountRequestEntity);

    AccountVO currentAccount();

    String lockAccount(String accountNo);

    void updateAccount(PersistAccountRequestEntity param);

    void updatePwd(UpdatePwdRequestEntity param);

    List<AccountVO> queryTeacher();

    String updateAvatar(String avatar);

    String generate();

    RegisterCode query();

    String checkInfo(String identityCard, String accountNo, String username, String tel, String pwd);

    String deleteAccount(String accountNo);

    String changeTel(String newTel);
}
