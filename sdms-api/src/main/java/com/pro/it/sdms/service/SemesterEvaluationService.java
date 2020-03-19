package com.pro.it.sdms.service;

import com.pro.it.sdms.entity.vo.SemesterEvaluationVO;

import java.util.List;

public interface SemesterEvaluationService {

    String SemesterEvalAdd(SemesterEvaluationVO vo);

    List<SemesterEvaluationVO> queryByAccountNo(String accountNo);
}
