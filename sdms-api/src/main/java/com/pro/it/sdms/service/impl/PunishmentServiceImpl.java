package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.dao.PunishmentDAO;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.Punishment;
import com.pro.it.sdms.entity.vo.PunishmentVO;
import com.pro.it.sdms.service.PunishmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PunishmentServiceImpl implements PunishmentService {

    @Autowired
    private PunishmentDAO punishmentDAO;

    @Autowired
    private AccountDAO accountDAO;

    /**
     * 添加某人的处分
     * @param vo
     * @return
     */
    @Override
    public BigDecimal addPunishment(PunishmentVO vo) {
        Punishment punishment = vo.toDTO();
        if (vo.getPunishmentNo() == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "student no required");
        }
        Account punishmentStudent = accountDAO.getAccountByAccountNo(vo.getPunishmentNo());
        punishment.setStudent(punishmentStudent);
        Punishment save = punishmentDAO.save(punishment);
        return save.getId();
    }

    /**
     * 取消处分
     * @param punishmentId 处分 Id
     * @return
     */
    @Override
    public BigDecimal cancelPunishment(BigDecimal punishmentId) {
        if (punishmentId == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "punishment id required");
        }
        Punishment one = punishmentDAO.getOne(punishmentId);
        one.setIsCancel((short) 1);
        one.setCancelTime(new Date());
        punishmentDAO.save(one);
        return one.getId();
    }

    /**
     * 查询某人的处分
     */
    @Override
    public List<PunishmentVO> queryPunishmentCur() {
        String accountNo = SecurityContextHolder.getContext().getAuthentication().getName();
        Account accountByAccountNo = accountDAO.getAccountByAccountNo(accountNo);
        if (accountByAccountNo == null) {
            throw new BadRequestException(Constants.Code.PARAM_FORMAT_ERROR, "account not exist");
        }
        List<Punishment> result = punishmentDAO.findAllByStudent(accountByAccountNo);
        List<PunishmentVO> ret = new ArrayList<>();
        result.forEach(item -> {
            ret.add(item.toVO());
        });
        return ret;
    }

    @Override
    public List<PunishmentVO> queryAll() {
        return punishmentDAO.findAll().stream().map(Punishment::toVO).collect(Collectors.toList());
    }


}
