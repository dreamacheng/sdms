package com.pro.it.sdms.entity.vo;

import com.pro.it.sdms.entity.dto.OrganizationApply;
import com.pro.it.sdms.enums.ApplyTypeEnum;
import com.pro.it.sdms.enums.ApprovalResult;
import com.pro.it.sdms.enums.SemesterEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class OrganizationApplyVO {

    /** 申请类型 */
    private String type;

    /** 申请文 */
    private String applyText;

    /** 申请附件 */
    private String applyAccessory;

    /** 申请学期 */
    private String semester;

    /** 审核人 */
    private String approver;

    /** 审批状态 */
    private String applyStatus;

    /** 审批意见 */
    private String applyComment;


    public OrganizationApply toDTO() {
        return OrganizationApply.builder()
                .type( ApplyTypeEnum.valueOf(getType()).getCode() )
                .approver( getApprover() )
                .applyComment( applyComment)
                .applyStatus(ApprovalResult.valueOf(getApprover()).getCode())
                .applyText(getApplyText())
                .applyAccessory(getApplyAccessory())
                .semester(SemesterEnum.valueOf(getSemester()).getCode())
                .build();
    }

}
