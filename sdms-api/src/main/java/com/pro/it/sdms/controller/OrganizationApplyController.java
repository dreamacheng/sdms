package com.pro.it.sdms.controller;

import com.pro.it.common.controller.BaseController;
import com.pro.it.sdms.controller.result.InfoAPIResult;
import com.pro.it.sdms.entity.vo.OrganizationApplyVO;
import com.pro.it.sdms.service.OrganizationApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrganizationApplyController extends BaseController {

    @Autowired
    private OrganizationApplyService organizationApplyService;

    private class URL {
        private static final String APPLY_MENTION = "/apply/add";
        private static final String APPLY_APPROVAL = "/apply/approval";
    }

    /**
     * 入团或入党申请
     * @param vo
     * @return
     */
    @PostMapping(URL.APPLY_MENTION)
    public InfoAPIResult<String> competitionAdd(OrganizationApplyVO vo) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("===> request method : [ Post ], request path [ {} ]", URL.APPLY_MENTION);
        log.info("===> request parameter {} : {} ", OrganizationApplyVO.class.getSimpleName(), vo);
        String s = organizationApplyService.addApply(vo);
        result.setInfo(s);
        log.info("===> response result {}", result);
        return result;
    }

    /**
     * 审核入团或入党申请
     * @param vo
     * @return
     */
    @PostMapping(URL.APPLY_APPROVAL)
    public InfoAPIResult<String> approval(OrganizationApplyVO vo) {
        InfoAPIResult<String> result = new InfoAPIResult<>();
        log.info("===> request method : [ Post ], request path [ {} ]", URL.APPLY_APPROVAL);
        log.info("===> request parameter {} : {} ", OrganizationApplyVO.class.getSimpleName(), vo);
        String s = organizationApplyService.approvalApply(vo);
        result.setInfo(s);
        log.info("===> response result {}", result);
        return result;
    }

}
