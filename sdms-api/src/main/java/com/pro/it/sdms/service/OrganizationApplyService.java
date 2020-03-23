package com.pro.it.sdms.service;

import com.pro.it.sdms.controller.request.ApplyResultRequestEntity;
import com.pro.it.sdms.entity.vo.OrganizationApplyVO;

import java.math.BigDecimal;
import java.util.List;

public interface OrganizationApplyService {

    /**
     * 添加入团/入党申请
     * @param vo
     * @return
     */
    BigDecimal addApply(OrganizationApplyVO vo);

    /**
     * 审核入团/入党申请
     * @param vo
     * @return
     */
    BigDecimal approvalApply(ApplyResultRequestEntity vo);

    /**
     * 查询当前用户指定类型申请
     * @param type
     * @return
     */
    OrganizationApplyVO queryCurAccount(String type);

    List<OrganizationApplyVO> queryCurAll();
}
