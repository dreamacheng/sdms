package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.utils.PageInfo;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.common.utils.QueryResult;
import com.pro.it.sdms.controller.request.QueryAccountRequestEntity;
import com.pro.it.sdms.controller.request.ResetPwdRequestEntity;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.dao.RegisterCodeDAO;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.RegisterCode;
import com.pro.it.sdms.controller.request.CreateAccountRequestEntity;
import com.pro.it.sdms.entity.vo.AccountVO;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.GenderEnum;
import com.pro.it.sdms.enums.IdentityEnum;
import com.pro.it.sdms.enums.PoliticsStatusEnum;
import com.pro.it.sdms.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private RegisterCodeDAO registerCodeDAO;

    /**
     * 注册用户
     * @param createAccountRequestEntity
     */
    @Override
    @Transactional
    public void registerAccount(CreateAccountRequestEntity createAccountRequestEntity) {
        verifyAccountInfoValid(createAccountRequestEntity);
        if ("ADMIN".equals(createAccountRequestEntity.getRole())) {
            if (StringUtils.isEmpty(createAccountRequestEntity.getRegisterCode())) {
                throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "Register Code missing");
            }
            RegisterCode registerCode = registerCodeDAO.getRegisterCodeByCode(createAccountRequestEntity.getRegisterCode());
            if (registerCode == null || StringUtils.isEmpty(registerCode.getCode())
                || registerCode.getAvailable().equals(2)) {
                throw new BadRequestException(Constants.Register.REGISTER_CODE_INVALID, "Register Code error or invalid");
            }
            registerCode.setAvailable((short) 1);
            registerCodeDAO.save(registerCode);
        }
        accountDAO.save(createAccountRequestEntity.toDTO());
    }

    /**
     * 验证用户信息是否有效
     * @param vo
     */
    public void verifyAccountInfoValid(CreateAccountRequestEntity vo) {
        if (vo == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "register parameter not be null");
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
    @Transactional
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

    /**
     * 查询用户列表
     * @param queryAccountRequestEntity
     * @return
     */
    @Override
    @Secured("ROLE_MANAGER")
    public QueryResult<AccountVO> queryAccount(QueryAccountRequestEntity queryAccountRequestEntity) {
        Pageable pageable = buildPageable(queryAccountRequestEntity.getPageInfo());
        Page<Account> all = accountDAO.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (!StringUtils.isEmpty(queryAccountRequestEntity.getAccountNo())) {
                predicateList.add(criteriaBuilder.equal(root.get("accountNo"), queryAccountRequestEntity.getAccountNo()));
            }
            if (!StringUtils.isEmpty(queryAccountRequestEntity.getDepartment())) {
                predicateList.add(criteriaBuilder.like(root.get("department"), queryAccountRequestEntity.getDepartment()));
            }
            if (!StringUtils.isEmpty(queryAccountRequestEntity.getLodgingHouse())) {
                predicateList.add(criteriaBuilder.like(root.get("lodgingHouse"), queryAccountRequestEntity.getLodgingHouse()));
            }
            if (!StringUtils.isEmpty(queryAccountRequestEntity.getPoliticsStatus())) {
                try {
                    predicateList.add(criteriaBuilder.equal(root.get("politicsStatus"), PoliticsStatusEnum.valueOf(queryAccountRequestEntity.getPoliticsStatus())));

                } catch (IllegalArgumentException e) {
                    throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "politics status not exist in this system");
                }
            }
            if (!StringUtils.isEmpty(queryAccountRequestEntity.getUsername())) {
                predicateList.add(criteriaBuilder.like(root.get("username"), queryAccountRequestEntity.getUsername()));
            }
            if (!StringUtils.isEmpty(queryAccountRequestEntity.getRole())) {
                predicateList.add(criteriaBuilder.equal(root.get("role"), queryAccountRequestEntity.getRole()));
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return criteriaBuilder.and(predicateList.toArray(predicates));
        }, pageable);

        QueryResult<AccountVO> queryResult = new QueryResult<>();
        List<AccountVO> list = new ArrayList<>();
        all.getContent().forEach(item -> {
            list.add(item.toVO());
        });
        queryResult.setResultlist(list);
        queryResult.setTotalrecord(all.getTotalElements());

        return queryResult;
    }

    @Override
    public AccountVO currentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "token illegal");
        }
        Account account = accountDAO.getAccountByAccountNo(authentication.getName());

        if (account == null) {
            throw new BadRequestException(Constants.ACCOUNT_NOT_EXIST, "account not exist");
        }
        return account.toVO();
    }

    @Override
    @Secured("ROLE_MANAGER")
    @Transactional
    public String lockAccount(String accountNo) {
        if (StringUtils.isEmpty(accountNo)) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "accountNo require");
        }
        Account accountByAccountNo = accountDAO.getAccountByAccountNo(accountNo);
        if (accountByAccountNo == null) {
            throw new BadRequestException(Constants.ACCOUNT_NOT_EXIST, "account not exist");
        }
        accountByAccountNo.setIsLock((short) 1);
        accountDAO.save(accountByAccountNo);
        return "success";
    }


    /**
     * 构建分页对象，并按更新时间与创建时间排序
     * @param pageInfo
     * @return
     */
    private Pageable buildPageable(PageInfo pageInfo) {
        if (pageInfo == null){
            //若分页对象为空，则不分页
            return PageRequest.of( 0, Integer.MAX_VALUE);
        }
        if(pageInfo.getPageNum() < 1){
            pageInfo.setPageNum(1);
        }
        if(pageInfo.getPageSize() < 1){
            pageInfo.setPageSize(10);
        }
        Sort.Order createTime = new Sort.Order(Sort.Direction.DESC, "createDatetime");
        List<Sort.Order> criteria = new ArrayList<>();
        criteria.add(createTime);
        Sort sort = Sort.by(criteria);
        return PageRequest.of(pageInfo.getPageNum() - 1, pageInfo.getPageSize(), sort);
    }

}
