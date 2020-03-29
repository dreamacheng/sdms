package com.pro.it.sdms.service;

import com.pro.it.sdms.entity.vo.ScholarshipVO;

import java.math.BigDecimal;
import java.util.List;

public interface ScholarshipService {

    ScholarshipVO getCurrentTerm(String term);

    List<ScholarshipVO> query();

    BigDecimal addApply(ScholarshipVO vo);
}
