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
        vo.setLeader(accountNo);
        vo.setCreateTime(new Date());
        SchoolClub save = schoolClubDAO.save(vo.toDTO());
        return save.getName();
    }

    /**
     * 查询所有社团
     * @return
     */
    @Override
    public List<SchoolClubVO> queryAll() {
        return schoolClubDAO.findAll().stream().map(SchoolClub::toVO).collect(Collectors.toList());
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
     * @param accountNo
     * @param clubId
     * @return
     */
    @Override
    public List<ClubMemberVO> queryDetail(String accountNo, BigDecimal clubId, String type) {
        List<ClubMember> all = clubMemberDAO.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (!StringUtils.isEmpty(accountNo)) {
                Join<ClubMember, Account> join = root.join("member", JoinType.INNER);
                predicateList.add(criteriaBuilder.equal(join.get("accountNo"), accountNo));
            }
            if (clubId != null) {
                Join<ClubMember, SchoolClub> join = root.join("club", JoinType.INNER);
                predicateList.add(criteriaBuilder.equal(join.get("id"), clubId));
            }
            if (!StringUtils.isEmpty(type)) {
                predicateList.add(criteriaBuilder.equal(root.get("memberStatus"), ApprovalResult.valueOf(type).getCode()));
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return criteriaBuilder.and(predicateList.toArray(predicates));
        });
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
        clubMember.setMemberStatus(ApprovalResult.valueOf(operation).getCode());
        return BaseCodeEnum.codeOf(ApprovalResult.class, clubMemberDAO.save(clubMember).getMemberStatus()).toString();
    }
}
