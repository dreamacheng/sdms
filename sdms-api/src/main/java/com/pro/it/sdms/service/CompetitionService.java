package com.pro.it.sdms.service;

import com.pro.it.sdms.controller.request.CompetitionResultRequestEntity;
import com.pro.it.sdms.entity.vo.CompetitionVO;
import com.pro.it.sdms.enums.BaseCodeEnum;

import java.math.BigDecimal;
import java.util.List;


public interface CompetitionService {

    String competitionAdd(CompetitionVO vo);

    List<CompetitionVO> queryAll();

    CompetitionVO query(BigDecimal id);

    String winnerAdd(CompetitionResultRequestEntity vo);

    String join(BigDecimal competitionId);

    List<CompetitionVO> queryPersonal();
}
