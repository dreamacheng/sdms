package com.pro.it.sdms.controller;

import com.pro.it.common.controller.BaseController;
import com.pro.it.sdms.controller.request.ApprovalClubRequestEntity;
import com.pro.it.sdms.controller.request.CreateClubApprovalRequestEntity;
import com.pro.it.sdms.controller.response.ClubApprovalListResponseEntity;
import com.pro.it.sdms.controller.result.InfoAPIResult;
import com.pro.it.sdms.controller.result.ListAPIResult;
import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.dto.ClubMember;
import com.pro.it.sdms.entity.vo.ClubApplyVO;
import com.pro.it.sdms.entity.vo.ClubMemberVO;
import com.pro.it.sdms.entity.vo.SchoolClubVO;
import com.pro.it.sdms.service.SchoolClubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Slf4j
public class SchoolClubController extends BaseController {

    @Autowired
    private SchoolClubService schoolClubService;

    private class URL {
        private static final String CLUB_ADD = "/club/add";
        private static final String CLUB_JOIN = "/club/join/{clubId}";
        private static final String CLUB_QUERY = "/club/query";
        private static final String QUERY_CLUB_OR_ACCOUNT ="/club/queryDetail";
        private static final String CLUB_JOIN_APPROVAL = "/club/join/approval";
        private static final String CLUB_APPROVAL = "/club/add/approval";
        private static final String CLUB_APPROVAL_LIST = "/club/approval/list";
        private static final String CLUB_MY_MENTION = "/club/myMention";
    }

    @PostMapping(URL.CLUB_ADD)
    public InfoAPIResult<String> addClub(@RequestBody SchoolClubVO vo) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.CLUB_ADD);
        log.info("=== > request parameter {} : {} ", SchoolClubVO.class.getSimpleName(), vo);
        String s = schoolClubService.addClub(vo);
        result.setInfo(s);
        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.CLUB_QUERY)
    public ListAPIResult<SchoolClubVO> query() {
        ListAPIResult<SchoolClubVO> result = new ListAPIResult<>();
        log.info("=== > request method : [ Get ], request path [ {} ]", URL.CLUB_QUERY);
        List<SchoolClubVO> list = schoolClubService.queryAll();
        result.setList(list);
        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.CLUB_JOIN)
    public InfoAPIResult<String> joinClub(@PathVariable("clubId") BigDecimal clubId) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.CLUB_JOIN);
        log.info("=== > request parameter clubId: {} ", clubId);
        String s = schoolClubService.joinClub(clubId);
        result.setInfo(s);
        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.QUERY_CLUB_OR_ACCOUNT)
    public ListAPIResult<ClubMemberVO> queryDetail(@RequestParam(value = "clubId", required = false) BigDecimal clubId,
                                                   @RequestParam(value = "type") String type) {
        ListAPIResult<ClubMemberVO> result = new ListAPIResult<>();
        log.info("=== > request method : [ Get ], request path [ {} ]", URL.QUERY_CLUB_OR_ACCOUNT);
        log.info("=== > request parameter type: {} , clubId: {} ", type, clubId);
        List<ClubMemberVO> list = schoolClubService.queryDetail(clubId, type);
        result.setList(list);
        log.info("=== > response result {}", result);
        return result;
    }

    @PostMapping(URL.CLUB_JOIN_APPROVAL)
    public InfoAPIResult<String> approval(@RequestBody ApprovalClubRequestEntity approvalClubRequestEntity) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.CLUB_JOIN_APPROVAL);
        log.info("=== > request parameter  {} : {} ", ApprovalClubRequestEntity.class.getSimpleName(), approvalClubRequestEntity);
        String status = schoolClubService.approval(approvalClubRequestEntity.getId(), approvalClubRequestEntity.getOperation());
        result.setInfo(status);
        log.info("=== > response result {}", result);
        return result;
    }

    @PostMapping(URL.CLUB_APPROVAL)
    public InfoAPIResult<BigDecimal> approvalAdd(@RequestBody CreateClubApprovalRequestEntity createClubApprovalRequestEntity) {
        InfoAPIResult<BigDecimal> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Post ], request path [ {} ]", URL.CLUB_APPROVAL);
        log.info("=== > request parameter  {} : {} ", CreateClubApprovalRequestEntity.class.getSimpleName(), createClubApprovalRequestEntity);
        BigDecimal status = schoolClubService.approvalAdd(createClubApprovalRequestEntity);
        result.setInfo(status);
        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.CLUB_APPROVAL_LIST)
    public InfoAPIResult<ClubApprovalListResponseEntity> queryAllApproval() {
        InfoAPIResult<ClubApprovalListResponseEntity> result = new InfoAPIResult<>();
        log.info("=== > request method : [ Get ], request path [ {} ]", URL.CLUB_APPROVAL_LIST);
        ClubApprovalListResponseEntity info = schoolClubService.queryAllApproval();
        result.setInfo(info);
        log.info("=== > response result {}", result);
        return result;
    }

    @GetMapping(URL.CLUB_MY_MENTION)
    public ListAPIResult<ClubApplyVO> myMention() {
        ListAPIResult<ClubApplyVO> result = new ListAPIResult<>();
        log.info("=== > request method : [ Get ], request path [ {} ]", URL.CLUB_MY_MENTION);
        List<ClubApplyVO> list = schoolClubService.myMention();
        result.setList(list);
        log.info("=== > response result {}", result);
        return result;
    }

}
