package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.dao.ScholarshipDAO;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.Scholarship;
import com.pro.it.sdms.entity.vo.ScholarshipVO;
import com.pro.it.sdms.enums.ApprovalResult;
import com.pro.it.sdms.enums.SemesterEnum;
import com.pro.it.sdms.service.ScholarshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScholarshipServiceImpl implements ScholarshipService {

    @Autowired
    private ScholarshipDAO scholarshipDAO;

    @Autowired
    private AccountDAO accountDAO;

    /**
     * 查询当前学生当前学期申请
     * @param term
     * @return
     */
    @Override
    public ScholarshipVO getCurrentTerm(String term) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountDAO.getAccountByAccountNo(name);
        Scholarship scholarship = scholarshipDAO.getByTermAndWinner(SemesterEnum.valueOf(term).getCode(), account);
        if (scholarship == null) {
            return null;
        }
        return scholarship.toVO();
    }

    /**
     * 查询当前学生所有获奖记录
     * @return
     */
    @Override
    public List<ScholarshipVO> query() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountDAO.getAccountByAccountNo(name);
        return scholarshipDAO.getAllByWinner(account).stream().map(Scholarship::toVO).collect(Collectors.toList());
    }

    /**
     * 添加奖学金申请
     * @param vo
     * @return
     */
    @Override
    public BigDecimal addApply(ScholarshipVO vo) {
        if (vo == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "parameter required");
        }
        String accountNo = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountDAO.getAccountByAccountNo(accountNo);
        vo.setTerm(account.toVO().getCurrentTerm());
        vo.setStatus(ApprovalResult.WaitForApproval.toString());
        Scholarship scholarship = scholarshipDAO.save(vo.toDTO().toBuilder().winner(account).build());
        return scholarship.getId();
    }
}
