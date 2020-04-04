package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.dao.ActivityPracticeDAO;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.ActivityPractice;
import com.pro.it.sdms.entity.vo.ActivityPracticeVO;
import com.pro.it.sdms.service.ActivityPracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityPracticeServiceImpl implements ActivityPracticeService {

    @Autowired
    private ActivityPracticeDAO activityPracticeDAO;

    @Autowired
    private AccountDAO accountDAO;

    /**
     * 添加活动
     * @param vo
     * @return
     */
    @Override
    public String activityAdd(ActivityPracticeVO vo) {
        if (vo == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "parameter required");
        }
        String no = SecurityContextHolder.getContext().getAuthentication().getName();
        Account current = accountDAO.getAccountByAccountNo(no);
        activityPracticeDAO.save(vo.toDTO().toBuilder().organization(current.getAccountInfo().getCollege()).build());
        return null;
    }

    /**
     * 查询所有活动
     * @return
     */
    @Override
    public List<ActivityPracticeVO> queryAll() {
        return activityPracticeDAO.findAll().stream().map(ActivityPractice::toVO).collect(Collectors.toList());
    }

    /**
     * 查询某个活动
     * @param id
     * @return
     */
    @Override
    public ActivityPracticeVO query(BigDecimal id) {
        if (id == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "activity id required");
        }
        ActivityPractice one = activityPracticeDAO.getOne(id);
        if (one == null) {
            throw new BadRequestException(Constants.Code.PARAM_FORMAT_ERROR, "activity not exist");
        }
        return one.toVO();
    }
}
