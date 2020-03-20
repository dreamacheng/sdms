package com.pro.it.sdms.service;

import com.pro.it.sdms.entity.vo.SemesterEvaluationVO;

import java.math.BigDecimal;
import java.util.List;

public interface SemesterEvaluationService {

    BigDecimal SemesterEval(SemesterEvaluationVO vo);

    List<SemesterEvaluationVO> queryByAccountNo(String accountNo);

    List<SemesterEvaluationVO> query();
}
