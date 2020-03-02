package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.dao.RegisterCodeDAO;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.RegisterCode;
import com.pro.it.sdms.entity.vo.AccountVO;
import com.pro.it.sdms.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private RegisterCodeDAO registerCodeDAO;

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
        if (verifyAccountInfoValid(vo)) {
            throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "Account has exists");
        }
        if ("ADMIN".equals(vo.getRole())) {
            if (StringUtils.isEmpty(vo.getRegisterCode())) {
                throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "Register Code missing");
            }
            RegisterCode registerCode = registerCodeDAO.getRegisterCodeByCode(vo.getRegisterCode());
            if (registerCode == null || StringUtils.isEmpty(registerCode.getCode())
                || registerCode.getAvailable().equals(Constants.REGISTER_CODE_INVALID)) {
                throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "Register Code error or invalid");
            }
        }
        accountDAO.save(voToDTO(vo));
    }

    @Override
    public boolean verifyAccountInfoValid(AccountVO vo) {
        if (vo == null) {
           throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "parameter error");
        }
        Account dto = voToDTO(vo);
        return accountDAO.exists(Example.of(dto));
    }


    private Account voToDTO (AccountVO vo){
        Account account = new Account();
        account.setUsername(vo.getUsername());
        String BCriptPassword = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(vo.getPassword());
        account.setPassword(BCriptPassword);
        account.setAccountNo(vo.getAccountNo());
        account.setAge(vo.getAge());
        account.setBirthday(vo.getBirthday());
        account.setIdentityCard(vo.getIdentityCard());
        account.setDepartment(vo.getDepartment());
        account.setLodgingHouse(vo.getLodgingHouse());
        account.setNation(vo.getNation());
        account.setPoliticsStatus(vo.getPoliticsStatus());
        account.setGender(vo.getGender());
        account.setTel(vo.getTel());
        account.setRole(vo.getRole());
        return account;
    }
}
