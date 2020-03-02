package com.pro.it.sdms.service;

import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.vo.AccountVO;

public interface AccountService {

    Account getUserByNo(String accountNo);

    void persistUserInfo(Account loginAccount);

    Account getUserByLoginInfo(String username, String password);

    void registerAccount(AccountVO vo);

    boolean verifyAccountInfoValid(AccountVO vo);
}
