package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.dao.OrganizationApplyDAO;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.OrganizationApply;
import com.pro.it.sdms.entity.vo.OrganizationApplyVO;
import com.pro.it.sdms.enums.ApplyTypeEnum;
import com.pro.it.sdms.service.OrganizationApplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrganizationApplyServiceImpl implements OrganizationApplyService {

    @Autowired
    private OrganizationApplyDAO organizationApplyDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public BigDecimal addApply(OrganizationApplyVO vo) {
        if (vo == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "parameter required");
        }
        OrganizationApply save = organizationApplyDAO.save(vo.toDTO());
        return save.getId();
    }

    @Override
    public String approvalApply(OrganizationApplyVO vo) {
        return null;
    }

    @Override
    public OrganizationApplyVO queryCurAccount(String queryType) {
        if (StringUtils.isEmpty(queryType)) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "type is required");
        }
        String proposer = SecurityContextHolder.getContext().getAuthentication().getName();
        short type = ApplyTypeEnum.valueOf(queryType).getCode();
        OrganizationApply byTypeAnAndAndProposer = organizationApplyDAO.findByTypeAndProposer(type, proposer);
        if (byTypeAnAndAndProposer == null) {
            throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "parameter error, query null");
        }
        OrganizationApplyVO vo = byTypeAnAndAndProposer.toVO();
        Account student = accountDAO.getAccountByAccountNo(vo.getProposer());
        Account teacher = accountDAO.getAccountByAccountNo(vo.getApprover());
        vo.setApproverName(teacher.getUsername());
        vo.setProposerName(student.getUsername());
        return vo;
    }

}
