package com.pro.it.sdms.service;

import com.pro.it.sdms.entity.vo.SemesterEvaluationVO;

import java.math.BigDecimal;
import java.util.List;

public interface SemesterEvaluationService {

    SemesterEvaluationVO SemesterEval(SemesterEvaluationVO vo);

    List<SemesterEvaluationVO> queryByAccountNo(String accountNo);

    List<SemesterEvaluationVO> query();

    SemesterEvaluationVO queryCurrent(String curTerm);
}
