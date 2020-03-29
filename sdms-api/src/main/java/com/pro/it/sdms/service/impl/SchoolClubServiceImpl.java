package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.dao.ClubMemberDAO;
import com.pro.it.sdms.dao.SchoolClubDAO;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.ClubMember;
import com.pro.it.sdms.entity.dto.SchoolClub;
import com.pro.it.sdms.entity.vo.ClubMemberVO;
import com.pro.it.sdms.entity.vo.SchoolClubVO;
import com.pro.it.sdms.enums.ApprovalResult;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.ClubLivenessEnum;
import com.pro.it.sdms.enums.MemBerIdentityEnum;
import com.pro.it.sdms.service.SchoolClubService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SchoolClubServiceImpl implements SchoolClubService {

    @Autowired
    private SchoolClubDAO schoolClubDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ClubMemberDAO clubMemberDAO;

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
        Account lead = accountDAO.getAccountByAccountNo(accountNo);
        vo.setLeader(accountNo);
        vo.setCreateTime(new Date());
        SchoolClub save = schoolClubDAO.save(vo.toDTO().toBuilder().number(1).logoUrl("https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png").build());
        ClubMember dto = new ClubMember();
        dto.setMember(lead);
        dto.setClub(save);
        dto.setLiveness(ClubLivenessEnum.LEVEL_1.getCode());
        dto.setPosition(MemBerIdentityEnum.BOSS.toString());
        dto.setMemberStatus(ApprovalResult.Approved.getCode());
        clubMemberDAO.save(dto);
        return save.getName();
    }

    /**
     * 查询所有社团
     * @return
     */
    @Override
    public List<SchoolClubVO> queryAll() {
        String accountNo = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountDAO.getAccountByAccountNo(accountNo);
        List<ClubMember> all = clubMemberDAO.getAllByMember(account);
        Set<BigDecimal> ids = all.stream().map(item -> item.getClub().getId()).collect(Collectors.toSet());
        return schoolClubDAO.findAll().stream()
                .filter(item -> !ids.contains(item.getId()))
                .map(SchoolClub::toVO).collect(Collectors.toList());
    }

    /**
     * 加入社团
     * @param clubId 社团Id
     * @return
     */
    @Override
    public String joinClub(BigDecimal clubId) {
        String accountNo = SecurityContextHolder.getContext().getAuthentication().getName();
        if (clubId == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "clubId required");
        }
        Account account = accountDAO.getAccountByAccountNo(accountNo);
        SchoolClub club = schoolClubDAO.getOne(clubId);
        if (account == null || club == null) {
            throw new BadRequestException(Constants.Code.PARAM_FORMAT_ERROR, "account or club not exist");
        }
        ClubMember dto = new ClubMember();
        dto.setMember(account);
        dto.setClub(club);
        dto.setLiveness(ClubLivenessEnum.LEVEL_1.getCode());
        dto.setPosition(MemBerIdentityEnum.NORMAL_MEMBER.toString());
        dto.setMemberStatus(ApprovalResult.WaitForApproval.getCode());
        return clubMemberDAO.save(dto).getPosition();
    }

    /**
     * 查询某学生的社团列表
     * or 查询某社团的社员列表
     * @param clubId
     * @param type
     * @return
     */
    @Override
    public List<ClubMemberVO> queryDetail(BigDecimal clubId, String type) {
        String accountNo = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountDAO.getAccountByAccountNo(accountNo);
        if ("Manager".equals(type)) {
            return clubMemberDAO.getAllByPositionIsNotAndMember(MemBerIdentityEnum.NORMAL_MEMBER.toString(), account)
                    .stream().map(ClubMember::toVO).collect(Collectors.toList());
        }
        List<ClubMember> all = clubMemberDAO.getAllByMemberStatusAndMember(ApprovalResult.valueOf(type).getCode(), account);
        if (clubId != null) {
            SchoolClub club = schoolClubDAO.getOne(clubId);
            all = clubMemberDAO.getAllByMemberStatusAndClub(ApprovalResult.valueOf(type).getCode(), club);
        }
        return all.stream().map(ClubMember::toVO).collect(Collectors.toList());
    }

    /**
     * 审批社员的入社申请
     * @param id
     * @param operation
     * @return
     */
    @Override
    public String approval(BigDecimal id, String operation) {
        if (id == null || StringUtils.isEmpty(operation)) {
            throw new BadRequestException(Constants.Code.PARAM_FORMAT_ERROR, "club member id or operation required");
        }
        ClubMember clubMember = clubMemberDAO.getOne(id);
        if (clubMember == null) {
            throw new BadRequestException(Constants.Code.PARAM_FORMAT_ERROR, "clubMember not exist");
        }
        if (ApprovalResult.Rejected.equals(operation)) {
            clubMemberDAO.delete(clubMember);
            return ApprovalResult.Rejected.toString();
        }
        if (ApprovalResult.Approved.toString().equals(operation)) {
            clubMember.getClub().setNumber(clubMember.getClub().getNumber() + 1);
            schoolClubDAO.save(clubMember.getClub());
        }
        clubMember.setMemberStatus(ApprovalResult.valueOf(operation).getCode());
        return BaseCodeEnum.codeOf(ApprovalResult.class, clubMemberDAO.save(clubMember).getMemberStatus()).toString();
    }
}
