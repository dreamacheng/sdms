package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.controller.request.CreateClubApprovalRequestEntity;
import com.pro.it.sdms.controller.response.ClubApprovalListResponseEntity;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.dao.ClubApplyDAO;
import com.pro.it.sdms.dao.ClubMemberDAO;
import com.pro.it.sdms.dao.SchoolClubDAO;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.ClubApply;
import com.pro.it.sdms.entity.dto.ClubMember;
import com.pro.it.sdms.entity.dto.SchoolClub;
import com.pro.it.sdms.entity.vo.ClubApplyVO;
import com.pro.it.sdms.entity.vo.ClubMemberVO;
import com.pro.it.sdms.entity.vo.SchoolClubVO;
import com.pro.it.sdms.enums.ApprovalResult;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.ClubLivenessEnum;
import com.pro.it.sdms.enums.MemBerIdentityEnum;
import com.pro.it.sdms.service.SchoolClubService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Autowired
    private ClubApplyDAO clubApplyDAO;

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
        ClubApply save = clubApplyDAO.save(vo.toDTO());
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
            throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "clubMember not exist");
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

    /**
     * 审批社团添加
     * @param createClubApprovalRequestEntity
     * @return
     */
    @Override
    public BigDecimal approvalAdd(CreateClubApprovalRequestEntity createClubApprovalRequestEntity) {
        if(createClubApprovalRequestEntity == null || createClubApprovalRequestEntity.getId() == null) {
            throw new BadRequestException(Constants.Code.PARAM_FORMAT_ERROR, "club id or required");
        }
        ClubApply clubApply = clubApplyDAO.getOne(createClubApprovalRequestEntity.getId());
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Account approver = accountDAO.getAccountByAccountNo(currentUser);
        if (clubApply == null) {
            throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "approval not exist");
        }
        if(ApprovalResult.Approved.toString().equals(createClubApprovalRequestEntity.getApplyStatus())) {
            Account lead = accountDAO.getAccountByAccountNo(clubApply.getCreateUser());
            String leader = lead.getUsername();
            SchoolClub club = SchoolClub.builder()
                    .number(1)
                    .logoUrl(clubApply.getLogoUrl())
                    .introduction(clubApply.getIntroduction())
                    .name(clubApply.getName())
                    .type(clubApply.getType())
                    .leader(leader).build();
            SchoolClub save = schoolClubDAO.save(club);
            ClubMember dto = new ClubMember();
            dto.setMember(lead);
            dto.setClub(save);
            dto.setLiveness(ClubLivenessEnum.LEVEL_1.getCode());
            dto.setPosition(MemBerIdentityEnum.BOSS.toString());
            dto.setMemberStatus(ApprovalResult.Approved.getCode());
            clubMemberDAO.save(dto);
        } else if(ApprovalResult.Rejected.toString().equals(createClubApprovalRequestEntity.getApplyStatus())) {

        } else {
            throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "operation not exist");
        }
        clubApply.setApplyStatus(ApprovalResult.valueOf(createClubApprovalRequestEntity.getApplyStatus()).getCode());
        clubApply.setApprover(approver.getUsername());
        clubApply.setApprovalComment(createClubApprovalRequestEntity.getApplyComment());
        clubApplyDAO.save(clubApply);
        return clubApplyDAO.save(clubApply).getId();
    }

    /**
     * 查询社团审批列表
     * @return
     */
    @Override
    @Secured("ROLE_MANAGER")
    public ClubApprovalListResponseEntity queryAllApproval() {
        List<ClubApplyVO> notList = clubApplyDAO.findAllByApplyStatusEquals(ApprovalResult.WaitForApproval.getCode()).stream().map(item -> {
            Account accountByAccountNo = accountDAO.getAccountByAccountNo(item.getCreateUser());
            return item.toVO().toBuilder().creator(accountByAccountNo.getUsername()).creatorNo(accountByAccountNo.getAccountNo()).build();
        }).collect(Collectors.toList());
        List<ClubApplyVO> list = clubApplyDAO.findAllByApplyStatusIsNot(ApprovalResult.WaitForApproval.getCode()).stream().map(item -> {
            Account accountByAccountNo = accountDAO.getAccountByAccountNo(item.getCreateUser());
            return item.toVO().toBuilder().creator(accountByAccountNo.getUsername()).creatorNo(accountByAccountNo.getAccountNo()).build();
        }).collect(Collectors.toList());
        ClubApprovalListResponseEntity result = new ClubApprovalListResponseEntity();
        result.setList(list);
        result.setNotList(notList);
        return result;
    }

    /**
     * 当前学生提交的创建社团申请
     * @return
     */
    @Override
    public List<ClubApplyVO> myMention() {
        String accountNo = SecurityContextHolder.getContext().getAuthentication().getName();
        return clubApplyDAO.findAllByCreateUser(accountNo).stream().map(item -> {
            Account accountByAccountNo = accountDAO.getAccountByAccountNo(accountNo);
            return item.toVO().toBuilder().creator(accountByAccountNo.getUsername()).creatorNo(accountByAccountNo.getAccountNo()).build();
        }).collect(Collectors.toList());
    }
}
