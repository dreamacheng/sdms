package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.controller.request.ApplyResultRequestEntity;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.dao.OrganizationApplyDAO;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.OrganizationApply;
import com.pro.it.sdms.entity.vo.OrganizationApplyVO;
import com.pro.it.sdms.enums.ApplyTypeEnum;
import com.pro.it.sdms.enums.ApprovalResult;
import com.pro.it.sdms.service.OrganizationApplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public BigDecimal approvalApply(ApplyResultRequestEntity vo) {
        if (vo == null || StringUtils.isEmpty(vo.getApplyComment())
                || StringUtils.isEmpty(vo.getApplyStatus()) || vo.getId() == null) {
            throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "parameter missing");
        }
        OrganizationApply dto = organizationApplyDAO.getOne(vo.getId());
        if (dto == null) {
            throw new BadRequestException(Constants.Code.PARAM_FORMAT_ERROR, "apply not exist");
        }
        dto.setApplyStatus(ApprovalResult.valueOf(vo.getApplyStatus()).getCode());
        dto.setApplyComment(vo.getApplyComment());
        dto.setApprovalTime(new Date());
        return organizationApplyDAO.save(dto).getId();
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

    @Override
    public List<OrganizationApplyVO> queryCurAll() {
        String approverNo = SecurityContextHolder.getContext().getAuthentication().getName();
        List<OrganizationApply> byApprover = organizationApplyDAO.findByApproverAndApplyStatus(approverNo, ApprovalResult.WaitForApproval.getCode());
        List<OrganizationApplyVO> voList = new ArrayList<>();
        byApprover.forEach(item -> {
            OrganizationApplyVO vo = item.toVO();
            Account proposer = accountDAO.getAccountByAccountNo(vo.getProposer());
            vo.setProposerName(proposer.getUsername());
            voList.add(vo);
        });
        return voList;
    }

}
