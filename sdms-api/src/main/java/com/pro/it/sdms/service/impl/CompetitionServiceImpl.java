package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.controller.request.CompetitionResultRequestEntity;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.dao.CompetitionDAO;
import com.pro.it.sdms.dao.CompetitionResultDAO;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.Competition;
import com.pro.it.sdms.entity.dto.CompetitionResult;
import com.pro.it.sdms.entity.vo.AccountVO;
import com.pro.it.sdms.entity.vo.CompetitionVO;
import com.pro.it.sdms.service.CompetitionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    @Autowired
    private CompetitionDAO competitionDAO;

    @Autowired
    private CompetitionResultDAO competitionResultDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public String competitionAdd(CompetitionVO vo) {
        if (vo == null ) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "parameter required");
        }
        Competition save = competitionDAO.save(vo.toDTO());
        return save.getName();
    }

    /**
     * 查询所有比赛信息
     * @return
     */
    @Override
    public List<CompetitionVO> queryAll() {
        String no = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountVO currentUser = accountDAO.getAccountByAccountNo(no).toVO();
        return competitionDAO.findAll().stream().
                map(Competition::toVO)
                .filter(item -> !item.getAttendeeList().contains(currentUser)).collect(Collectors.toList());
    }

    /**
     * 查询当前学生参加的比赛
     * @return
     */
    @Override
    public List<CompetitionVO> queryPersonal() {
        String no = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountVO currentUser = accountDAO.getAccountByAccountNo(no).toVO();
        return competitionDAO.findAll().stream().
                map(Competition::toVO)
                .filter(item -> item.getAttendeeList().contains(currentUser)).collect(Collectors.toList());
    }

    /**
     * 查询某个比赛
     * @param id
     * @return
     */
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

    /**
     * 添加获奖者
     * @param vo
     * @return
     */
    @Override
    public String winnerAdd(CompetitionResultRequestEntity vo) {
        if (vo == null || vo.getCompetitionId() == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "competition id required");
        }
        Competition one = competitionDAO.getOne(vo.getCompetitionId());
        if (one == null) {
            throw new BadRequestException(Constants.Code.PARAM_FORMAT_ERROR, "competition not exist");
        }
        if (one.getPrizewinnerList() == null) {
            one.setPrizewinnerList(new ArrayList<>());
        }
        CompetitionResult competitionResult = competitionResultDAO.save(vo.toDTO());
        one.getPrizewinnerList().add(competitionResult);
        return competitionDAO.save(one).getName();
    }

    /**
     * 参加比赛
     * @param competitionId 比赛Id
     * @return
     */
    @Override
    public String join(BigDecimal competitionId) {
        String accountNo = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountDAO.getAccountByAccountNo(accountNo);
        Competition competition = competitionDAO.getOne(competitionId);
        if (account == null || competition == null) {
            throw new BadRequestException(Constants.Code.PARAM_FORMAT_ERROR, "account or competition not exist");
        }
        if (competition.getAttendeeList() == null) {
            competition.setAttendeeList(new ArrayList<>());
        }
        competition.getAttendeeList().add(account);
        return competitionDAO.save(competition).getName();
    }

}
