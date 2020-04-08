package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.utils.PageInfo;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.common.utils.QueryResult;
import com.pro.it.sdms.controller.request.QueryAccountRequestEntity;
import com.pro.it.sdms.controller.request.ResetPwdRequestEntity;
import com.pro.it.sdms.controller.request.UpdatePwdRequestEntity;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.dao.RegisterCodeDAO;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.RegisterCode;
import com.pro.it.sdms.controller.request.PersistAccountRequestEntity;
import com.pro.it.sdms.entity.vo.AccountVO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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
    public void registerAccount(PersistAccountRequestEntity createAccountRequestEntity) {
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
            registerCode.setAvailable((short) 2);
            registerCodeDAO.save(registerCode);
        }
        accountDAO.save(createAccountRequestEntity.toDTO());
    }

    /**
     * 验证用户信息是否有效
     * @param vo
     */
    public void verifyAccountInfoValid(PersistAccountRequestEntity vo) {
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
                    predicateList.add(criteriaBuilder.equal(root.get("politicsStatus"), PoliticsStatusEnum.valueOf(queryAccountRequestEntity.getPoliticsStatus()).getCode()));

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
        List<AccountVO> list = all.getContent().stream().map(Account::toVO).collect(Collectors.toList());
        queryResult.setResultList(list);
        queryResult.setTotalRecord(all.getTotalElements());

        return queryResult;
    }

    /**
     * 获取当前用户信息
     * @return
     */
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

    /**
     * 锁定用户
     * @param accountNo 用户编号
     * @return
     */
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
        if (accountByAccountNo.getIsLock() == 0) {
            accountByAccountNo.setIsLock((short) 1);
        } else {
            accountByAccountNo.setIsLock((short) 0);
        }
        accountDAO.save(accountByAccountNo);
        return "success";
    }

    /**
     * 更新用户信息
     * @param param
     */
    @Override
    @Transactional
    public void updateAccount(PersistAccountRequestEntity param) {
        if (param == null || param.getId() == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "invalid update parameter");
        }
        Account dto = accountDAO.getOne(param.getId());
        if (dto == null) {
            throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "update account not exist");
        }
        dto.setPoliticsStatus(PoliticsStatusEnum.valueOf(param.getPoliticsStatus()).getCode());
        dto.setUsername(param.getUsername());
        dto.getAccountInfo().setLodgingHouse(param.getLodgingHouse());
        dto.getAccountInfo().setCollege(param.getCollege());
        dto.getAccountInfo().setMajor(param.getMajor());
        accountDAO.save(dto);
    }

    @Override
    @Transactional
    public void updatePwd(UpdatePwdRequestEntity param) {
        if (param == null || StringUtils.isEmpty(param.getNewPwd()) || StringUtils.isEmpty(param.getOldPwd())) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "invalid update parameter");
        }
        String accountNo = SecurityContextHolder.getContext().getAuthentication().getName();
        Account dto = accountDAO.getAccountByAccountNo(accountNo);
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String newPwd = encoder.encode(param.getNewPwd());
        if (encoder.matches(param.getOldPwd(), dto.getPassword())) {
            dto.setPassword(newPwd);
            accountDAO.save(dto);
        } else {
            throw new BadRequestException(Constants.PWD_ERROR, "old password error");
        }
    }

    /**
     * 查询教师
     * @return
     */
    @Override
    public List<AccountVO> queryTeacher() {
        return accountDAO.findAllByRole(IdentityEnum.MANAGER.toString()).stream().map(Account::toVO).collect(Collectors.toList());
    }

    /**
     * 更新头像
     * @param avatar
     * @return
     */
    @Override
    public String updateAvatar(String avatar) {
        if (StringUtils.isEmpty(avatar)) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "avatar required");
        }
        String no = SecurityContextHolder.getContext().getAuthentication().getName();
        Account accountByAccountNo = accountDAO.getAccountByAccountNo(no);
        accountByAccountNo.setAvatar(avatar);
        return accountDAO.save(accountByAccountNo).getAvatar();
    }

    /**
     * 生成注册码
     * @return
     */
    @Override
    @Secured("ROLE_MANAGER")
    public String generate() {
        String generateCode = generateCode();
        RegisterCode registerCode = RegisterCode.builder().available((short) 1).code(generateCode).build();
        return registerCodeDAO.save(registerCode).getCode();
    }

    /**
     * 查询当前用户生成的注册码
     * @return
     */
    @Override
    public RegisterCode query() {
        String no = SecurityContextHolder.getContext().getAuthentication().getName();
        List<RegisterCode> registerCodeByCreateUser = registerCodeDAO.getRegisterCodeByCreateUser(no).stream().filter(item -> item.getAvailable().equals((short) 1)).collect(Collectors.toList());
        if (registerCodeByCreateUser.isEmpty()) {
            return RegisterCode.builder().available((short)2).build();
        }
        return registerCodeByCreateUser.get(0);
    }

    @Override
    public String checkInfo(String identityCard, String accountNo, String tel, String pwd) {
        Account account = accountDAO.findByAccountNoAndIdentityCardAndTel(accountNo, identityCard, tel);
        if (account == null) {
            throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "user not exist");
        }
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String newPwd = encoder.encode(pwd);
        account.setPassword(newPwd);
        return accountDAO.save(account).getAccountNo();
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

    private String generateCode() {
        char[] codeArr = new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9','0'};
        Random random = new Random();
        String code = "";
        for (int i = 0; i< 4; i++) {
            code = "" + codeArr[random.nextInt(35)] + codeArr[random.nextInt(35)] + codeArr[random.nextInt(35)] + codeArr[random.nextInt(35)] + codeArr[random.nextInt(35)];
        }
        return code;
    }
}
