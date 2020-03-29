package com.pro.it.sdms.service;

import com.pro.it.sdms.entity.vo.PunishmentVO;

import java.math.BigDecimal;
import java.util.List;

public interface PunishmentService {

    BigDecimal addPunishment(PunishmentVO vo);

    BigDecimal cancelPunishment(BigDecimal punishmentId);

    List<PunishmentVO> queryPunishmentByAccountNo(String accountNo);

}
