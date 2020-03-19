package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.dao.CompetitionDAO;
import com.pro.it.sdms.entity.dto.Competition;
import com.pro.it.sdms.entity.vo.CompetitionVO;
import com.pro.it.sdms.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
