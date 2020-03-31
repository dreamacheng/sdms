package com.pro.it.sdms.service;

import com.pro.it.sdms.controller.response.EvaluateListResponseEntity;
import com.pro.it.sdms.entity.vo.SemesterEvaluationVO;

import java.util.List;

public interface SemesterEvaluationService {

    SemesterEvaluationVO SemesterEval(SemesterEvaluationVO vo);

    List<SemesterEvaluationVO> queryByAccountNo(String accountNo);

    EvaluateListResponseEntity query();

    SemesterEvaluationVO queryCurrent(String curTerm);
}
