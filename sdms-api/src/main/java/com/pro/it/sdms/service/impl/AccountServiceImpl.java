package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.controller.request.ResetPwdRequestEntity;
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

import java.util.Optional;

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

    /**
     * 注册用户
     * @param vo
     */
    @Override
    public void registerAccount(AccountVO vo) {
        verifyAccountInfoValid(vo);
        if ("ADMIN".equals(vo.getRole())) {
            if (StringUtils.isEmpty(vo.getRegisterCode())) {
                throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "Register Code missing");
            }
            RegisterCode registerCode = registerCodeDAO.getRegisterCodeByCode(vo.getRegisterCode());
            if (registerCode == null || StringUtils.isEmpty(registerCode.getCode())
                || registerCode.getAvailable().equals(Constants.REGISTER_CODE_INVALID)) {
                throw new BadRequestException(Constants.Register.REGISTER_CODE_INVALID, "Register Code error or invalid");
            }
        }
        accountDAO.save(voToDTO(vo));
    }

    @Override
    public void verifyAccountInfoValid(AccountVO vo) {
        if (vo == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "parameter error");
        }
        if (!accountDAO.getAccountsByAccountNo(vo.getAccountNo()).isEmpty()) {
            throw new BadRequestException(Constants.Register.ACCOUNT_NO_EXIST, "account no has been exist");
        }
        if (!accountDAO.getAccountsByIdentityCard(vo.getIdentityCard()).isEmpty()) {
            throw new BadRequestException(Constants.Register.IDENTITY_CARD_EXIST, "identity card has been exist");
        }
    }

    /**
     * 重置密码
     * @param resetPwdRequestEntity
     */
    @Override
    public void resetPwd(ResetPwdRequestEntity resetPwdRequestEntity) {
        Account dto = new Account();
        dto.setUsername(resetPwdRequestEntity.getUsername());
        dto.setIdentityCard(resetPwdRequestEntity.getIdentityCard());
        dto.setAccountNo(resetPwdRequestEntity.getAccountNo());
        Optional<Account> optionalAccount = accountDAO.findOne(Example.of(dto));
        if (optionalAccount.get() == null) {
            throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "user not exist");
        }
        Account account = optionalAccount.get();
        accountDAO.save(dto);
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
