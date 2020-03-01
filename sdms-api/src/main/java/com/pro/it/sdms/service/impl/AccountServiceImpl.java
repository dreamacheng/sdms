package com.pro.it.sdms.service.impl;

import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.vo.AccountVO;
import com.pro.it.sdms.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public Account getUserByNo(String accountNo) {
        return accountDAO.getAccountByAccountNo(accountNo);
    }

    @Override
    public void persistUserInfo(Account loginAccount) {
        accountDAO.save(loginAccount);
    }

    @Override
    public Account getUserByLoginInfo(String username, String password) {
        return accountDAO.getAccountByAccountNoAndPassword(username, password);
    }

    @Override
    public void registerAccount(AccountVO vo) {
        if ("ADMIN".equals(vo.getRole()) && StringUtils.isEmpty(vo.getRegisterCode())) {


        }
        accountDAO.save(VOToDTO(vo));
    }


    private Account VOToDTO (AccountVO vo){
        Account account = new Account();
        return account;
    }
}
