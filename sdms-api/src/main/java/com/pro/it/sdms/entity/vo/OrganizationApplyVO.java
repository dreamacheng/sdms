package com.pro.it.sdms.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.it.sdms.entity.dto.OrganizationApply;
import com.pro.it.sdms.enums.ApplyTypeEnum;
import com.pro.it.sdms.enums.ApprovalResult;
import com.pro.it.sdms.enums.SemesterEnum;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@ToString
@Getter
@Setter
@Builder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationApplyVO {

    private BigDecimal id;

    /** 申请类型 */
    private String type;

    /** 申请文 */
    private String applyText;

    /** 申请附件 */
    private String applyAccessory;

    /** 申请人 */
    private String proposer;

    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT")
    private Date applyTime;

    /** 申请人姓名 */
    private String proposerName;

    /** 审批人姓名 */
    private String approverName;

    /** 审核人 */
    private String approver;

    /** 审批状态 */
    private String applyStatus;

    /** 审批意见 */
    private String applyComment;


    public OrganizationApply toDTO() {
        return OrganizationApply.builder()
                .type( ApplyTypeEnum.valueOf(getType()).getCode() )
                .approver(getApprover())
                .proposer(getProposer())
                .applyComment(getApplyComment())
                .applyStatus(ApprovalResult.valueOf(getApplyStatus()).getCode())
                .applyText(getApplyText())
                .applyAccessory(getApplyAccessory())
                .applyTime(getApplyTime())
                .build();
    }

}
