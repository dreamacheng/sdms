package com.pro.it.sdms.service;

import com.pro.it.sdms.controller.request.CreateClubApprovalRequestEntity;
import com.pro.it.sdms.controller.response.ClubApprovalListResponseEntity;
import com.pro.it.sdms.entity.vo.ClubApplyVO;
import com.pro.it.sdms.entity.vo.ClubMemberVO;
import com.pro.it.sdms.entity.vo.SchoolClubVO;

import java.math.BigDecimal;
import java.util.List;

public interface SchoolClubService {

    String addClub(SchoolClubVO vo);

    List<SchoolClubVO> queryAll();

    String joinClub(BigDecimal clubId);

    List<ClubMemberVO> queryDetail(BigDecimal clubId, String type);

    String approval(BigDecimal id, String operation);

    BigDecimal approvalAdd(CreateClubApprovalRequestEntity createClubApprovalRequestEntity);

    ClubApprovalListResponseEntity queryAllApproval();

    List<ClubApplyVO> myMention();
}
