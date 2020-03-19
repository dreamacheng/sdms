package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.dao.SemesterEvaluationDAO;
import com.pro.it.sdms.entity.dto.SemesterEvaluation;
import com.pro.it.sdms.entity.vo.SemesterEvaluationVO;
import com.pro.it.sdms.service.SemesterEvaluationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SemesterEvaluationServiceImpl implements SemesterEvaluationService {


    @Autowired
    private SemesterEvaluationDAO semesterEvaluationDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public String SemesterEvalAdd(SemesterEvaluationVO vo) {
        if (vo == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "parameter required");
        }
        SemesterEvaluation save = semesterEvaluationDAO.save(vo.toDTO());
        return save.getStudentNo();
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
}
