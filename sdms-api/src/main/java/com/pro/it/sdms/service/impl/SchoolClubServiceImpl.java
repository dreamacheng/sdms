package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.dao.SchoolClubDAO;
import com.pro.it.sdms.entity.dto.SchoolClub;
import com.pro.it.sdms.entity.vo.SchoolClubVO;
import com.pro.it.sdms.service.SchoolClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SchoolClubServiceImpl implements SchoolClubService {

    @Autowired
    private SchoolClubDAO schoolClubDAO;

    /**
     * 创建社团
     * @param vo
     * @return
     */
    @Override
    public String addClub(SchoolClubVO vo) {
        if (vo == null || vo.getName() == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "parameter required");
        }
        String accountNo = SecurityContextHolder.getContext().getAuthentication().getName();
        vo.setLeader(accountNo);
        vo.setCreateTime(new Date());
        SchoolClub save = schoolClubDAO.save(vo.toDTO());
        return save.getName();
    }
}
