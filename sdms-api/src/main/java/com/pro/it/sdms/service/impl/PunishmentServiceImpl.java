package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.dao.PunishmentDAO;
import com.pro.it.sdms.entity.dto.Punishment;
import com.pro.it.sdms.entity.vo.PunishmentVO;
import com.pro.it.sdms.service.PunishmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PunishmentServiceImpl implements PunishmentService {

    @Autowired
    private PunishmentDAO punishmentDAO;

    /**
     * 添加某人的处分
     * @param vo
     * @return
     */
    @Override
    public String addPunishment(PunishmentVO vo) {
        Punishment punishment = vo.toDTO();
        Punishment save = punishmentDAO.save(punishment);
        return save.getStudentNo();
    }

    /**
     * 取消处分
     * @param punishmentId 处分 Id
     * @return
     */
    @Override
    public String cancelPunishment(BigDecimal punishmentId) {
        if (punishmentId == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "punishment id required");
        }
        Punishment one = punishmentDAO.getOne(punishmentId);
        one.setIsCancel((short) 1);
        one.setCancelTime(new Date());
        punishmentDAO.save(one);
        return one.getStudentNo();
    }

    /**
     * 查询某人的处分
     */
    @Override
    public List<PunishmentVO> queryPunishmentByAccountNo(String accountNo) {

        if (StringUtils.isEmpty(accountNo)) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "account no required");
        }
        List<Punishment> result = punishmentDAO.findAllByStudentNo(accountNo);
        List<PunishmentVO> ret = new ArrayList<>();
        result.forEach(item -> {
            ret.add(item.toVO());
        });
        return ret;
    }



}
