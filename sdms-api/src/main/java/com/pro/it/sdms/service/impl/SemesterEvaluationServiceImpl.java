package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.common.utils.VerifyUtil;
import com.pro.it.sdms.dao.SemesterEvaluationDAO;
import com.pro.it.sdms.entity.dto.SemesterEvaluation;
import com.pro.it.sdms.entity.vo.SemesterEvaluationVO;
import com.pro.it.sdms.service.SemesterEvaluationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SemesterEvaluationServiceImpl implements SemesterEvaluationService {


    @Autowired
    private SemesterEvaluationDAO semesterEvaluationDAO;


    @Override
    public BigDecimal SemesterEval(SemesterEvaluationVO vo) {
        if (vo == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "parameter required");
        }
        if (vo.getId() != null) {
            String currentRole = VerifyUtil.getCurrentRole();
            SemesterEvaluation one = semesterEvaluationDAO.getOne(vo.getId());
            if (one == null) {
                throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "semester evaluation id error");
            }
            if (currentRole.equals("ROLE_STUDENT")) { // 学生自评
                one.setSelfEval(vo.getSelfEval());
            } else { // 老师评价
                one.setTeacherEval(vo.getTeacherEval());
                one.setTeacherNo(vo.getTeacherNo());
                one.setTeacherName(vo.getTeacherName());
                one.setGrade(vo.getGrade());
            }
            semesterEvaluationDAO.save(one);
        }
        SemesterEvaluation save = semesterEvaluationDAO.save(vo.toDTO());
        return save.getId();
    }

    /**
     * 查询某学生学期评价列表
     * @param accountNo 学号
     * @return
     */
    @Override
    public List<SemesterEvaluationVO> queryByAccountNo(String accountNo) {
        if (StringUtils.isEmpty(accountNo)) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "accountNo required");
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
}
