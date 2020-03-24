package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.dao.CompetitionDAO;
import com.pro.it.sdms.entity.dto.Competition;
import com.pro.it.sdms.entity.vo.CompetitionVO;
import com.pro.it.sdms.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    @Autowired
    private CompetitionDAO competitionDAO;

    @Override
    public String competitionAdd(CompetitionVO vo) {
        if (vo == null ) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "parameter required");
        }
        Competition save = competitionDAO.save(vo.toDTO());
        return save.getName();
    }

    @Override
    public List<CompetitionVO> queryAll() {
        return competitionDAO.findAll().stream().map(Competition::toVO).collect(Collectors.toList());
    }

    @Override
    public CompetitionVO query(BigDecimal id) {
        if (id == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "competition id required");
        }
        Competition one = competitionDAO.getOne(id);
        if (one == null) {
            throw new BadRequestException(Constants.Code.PARAM_FORMAT_ERROR, "competition not exist");
        }
        return one.toVO();
    }
}
