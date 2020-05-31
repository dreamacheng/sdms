package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.dao.ActivityPracticeDAO;
import com.pro.it.sdms.dao.ActivityResultDAO;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.ActivityPractice;
import com.pro.it.sdms.entity.dto.ActivityResult;
import com.pro.it.sdms.entity.dto.Competition;
import com.pro.it.sdms.entity.vo.ActivityPracticeVO;
import com.pro.it.sdms.entity.vo.ActivityResultVO;
import com.pro.it.sdms.service.ActivityPracticeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityPracticeServiceImpl implements ActivityPracticeService {

    @Autowired
    private ActivityPracticeDAO activityPracticeDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ActivityResultDAO activityResultDAO;

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
        String organization = current.getAccountInfo().getCollege();
        if (StringUtils.isEmpty(current.getAccountInfo().getCollege())) {
            organization = "成都理工大学";
        }
        activityPracticeDAO.save(vo.toDTO().toBuilder().organization(organization).build());
        return null;
    }

    /**
     * 查询所有活动
     * @return
     */
    @Override
    public List<ActivityPracticeVO> queryAll() {
        String no = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountDAO.getAccountByAccountNo(no);
        List<BigDecimal> member = activityResultDAO.findAllByMember(account)
                .stream().map(item -> item.getActivityPractice().getId()).collect(Collectors.toList());
        return activityPracticeDAO.findAll().stream()
                .map(ActivityPractice::toVO)
                .filter(item ->  !member.contains(item.getId()))
                .collect(Collectors.toList());
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

    /**
     * 参加活动
     * @param activityId 活动Id
     * @return
     */
    @Override
    public String join(BigDecimal activityId) {
        if (activityId == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "activityId required");
        }
        String accountNo = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountDAO.getAccountByAccountNo(accountNo);
        ActivityPractice activityPractice = activityPracticeDAO.getOne(activityId);
        if (account == null || activityPractice == null) {
            throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "account or activity not exist");
        }
        if (activityPractice.getAttendeeList() == null) {
            activityPractice.setAttendeeList(new ArrayList<>());
        }
        activityPractice.getAttendeeList().add(account);
        activityPracticeDAO.save(activityPractice).getTitle();
        ActivityResult activityResult = new ActivityResult();
        activityResult.setActivityPractice(activityPractice);
        activityResult.setMember(account);
        return activityResultDAO.save(activityResult).getSummary();
    }

    /**
     * 查询当前用户参加的活动
     * @return
     */
    @Override
    public List<ActivityResultVO> queryJoin() {
        String accountNo = SecurityContextHolder.getContext().getAuthentication().getName();
        Account current = accountDAO.getAccountByAccountNo(accountNo);
        return activityResultDAO.findAllByMember(current).stream().map(ActivityResult::toVO).collect(Collectors.toList());
    }

    /**
     * 添加活动总结
     * @param activityResultId 活动总结表id
     * @param summary 总结
     * @return
     */
    @Override
    public String summary(BigDecimal activityResultId, String summary) {
        if (StringUtils.isEmpty(summary)) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "summary required");
        }
        ActivityResult activityResult = activityResultDAO.getOne(activityResultId);
        if (activityResult == null) {
            throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "acctivity Practice not exist");
        }
        activityResult.setSummary(summary);
        return activityResultDAO.save(activityResult).getUpdateUser();
    }

    /**
     * 查询某生某活动参加履历
     * @param accountNo
     * @param activityId
     * @return
     */
    @Override
    public ActivityResultVO summaryView(String accountNo, BigDecimal activityId) {
        if (StringUtils.isEmpty(accountNo) || activityId == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "accountNo or activityId required");
        }
        Account student = accountDAO.getAccountByAccountNo(accountNo);
        ActivityPractice activityPractice = activityPracticeDAO.getOne(activityId);
        if (student == null || activityPractice == null) {
            throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "account or Practice not exist");
        }
        ActivityResult result = activityResultDAO.getByActivityPracticeAndMember(activityPractice, student);
        if (result == null) {
            throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "result not exist");
        }
        return result.toVO();
    }
}
