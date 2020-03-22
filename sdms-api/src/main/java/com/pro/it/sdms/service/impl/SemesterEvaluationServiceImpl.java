package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.common.utils.VerifyUtil;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.dao.SemesterEvaluationDAO;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.AccountInfo;
import com.pro.it.sdms.entity.dto.SemesterEvaluation;
import com.pro.it.sdms.entity.vo.SemesterEvaluationVO;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.SemesterEnum;
import com.pro.it.sdms.service.SemesterEvaluationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SemesterEvaluationServiceImpl implements SemesterEvaluationService {


    @Autowired
    private SemesterEvaluationDAO semesterEvaluationDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Override
    @Transactional
    public SemesterEvaluationVO SemesterEval(SemesterEvaluationVO vo) {
        if (vo == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "parameter required");
        }
        if (vo.getId() != null) {
            String currentRole = VerifyUtil.getCurrentRole();
            SemesterEvaluation one = semesterEvaluationDAO.getOne(vo.getId());
            Account accountByAccountNo = accountDAO.getAccountByAccountNo(one.getStudentNo());
            if (one == null) {
                throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "semester evaluation id error");
            }
            if (currentRole.equals("ROLE_STUDENT")) { // 学生自评
                one.setSelfEval(vo.getSelfEval());
            } else { // 老师评价
                one.setTeacherEval(vo.getTeacherEval());
                one.setGrade(vo.getGrade());
                one.setTeacherName(accountByAccountNo.getUsername());
                one.setTeacherNo(accountByAccountNo.getAccountNo());
            }
            one.setStudentInfo(accountByAccountNo.getAccountInfo());
            return semesterEvaluationDAO.save(one).toVO();
        }
        Account accountByAccountNo = accountDAO.getAccountByAccountNo(vo.getStudentNo());
        SemesterEvaluation per = vo.toDTO();
        per.setStudentInfo(accountByAccountNo.getAccountInfo());
        SemesterEvaluation save = semesterEvaluationDAO.save(per);
        return save.toVO();
    }

    /**
     * 查询某学生学期评价列表
     * @param accountNo 学号
     * @return
     */
    @Override
    public List<SemesterEvaluationVO> queryByAccountNo(String accountNo) {
        if (StringUtils.isEmpty(accountNo)) {
            accountNo = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        List<SemesterEvaluation> allByStudentNo = semesterEvaluationDAO.findAllByStudentNo(accountNo);
        List<SemesterEvaluationVO> resultList = new ArrayList<>();
        allByStudentNo.forEach(item -> {
            resultList.add(item.toVO());
        });
        return resultList;
    }

    /**
     * 查询所有学生的评价
     * @return
     */
    @Override
    @Secured("ROLE_MANAGER")
    public List<SemesterEvaluationVO> query() {
        List<SemesterEvaluation> all = semesterEvaluationDAO.findAll();
        List<SemesterEvaluationVO> resultList = new ArrayList<>();
        all.forEach(item -> {
            resultList.add(item.toVO());
        });
        return resultList;
    }

    /**
     * 查询当前学生当前学期期末评价
     * @param curTerm
     * @return
     */
    @Override
    public SemesterEvaluationVO queryCurrent(String curTerm) {
        if (StringUtils.isEmpty(curTerm)) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "current Term required");
        }
        String studentNo = SecurityContextHolder.getContext().getAuthentication().getName();
        SemesterEvaluation byStudentNoAndSemester = semesterEvaluationDAO.findByStudentNoAndSemester(studentNo, SemesterEnum.valueOf(curTerm).getCode());
        if (byStudentNoAndSemester == null) {
            return null;
        }
        return byStudentNoAndSemester.toVO();
    }
}
