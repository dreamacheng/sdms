package com.pro.it.sdms.service;

import com.pro.it.sdms.entity.vo.OrganizationApplyVO;

public interface OrganizationApplyService {

    /**
     * 添加入团/入党申请
     * @param vo
     * @return
     */
    String addApply(OrganizationApplyVO vo);

    /**
     * 审核入团/入党申请
     * @param vo
     * @return
     */
    String approvalApply(OrganizationApplyVO vo);
}
