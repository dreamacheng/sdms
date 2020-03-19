package com.pro.it.sdms.entity.dto;


import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.OrganizationApplyVO;
import com.pro.it.sdms.enums.ApplyTypeEnum;
import com.pro.it.sdms.enums.ApprovalResult;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.SemesterEnum;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "sdms_organization_apply")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder
public class OrganizationApply extends BaseDTO {

    /** 申请类型 */
    @Column(name = "type", nullable = false, columnDefinition = "int")
    private Short type;

    /** 申请文 */
    @Column(name = "apply_text", nullable = false, columnDefinition = "varchar(1000)")
    private String applyText;

    /** 申请附件 */
    @Column(name = "apply_accessory", columnDefinition = "varchar(100)")
    private String applyAccessory;

    /** 申请学期 */
    @Column(name = "semester", nullable = false, columnDefinition = "int")
    private Short semester;

    /** 审核人 */
    @Column(name = "approver_no", nullable = false, columnDefinition = "int")
    private String approver;

    /** 审批状态 */
    @Column(name = "apply_status", nullable = false, columnDefinition = "int")
    private Short applyStatus;

    /** 审批意见 */
    @Column(name = "apply_comment", nullable = false, columnDefinition = "varchar(300)")
    private String applyComment;

    public OrganizationApplyVO toVO() {
        return OrganizationApplyVO.builder()
                .type( BaseCodeEnum.codeOf( ApplyTypeEnum.class, getType()).toString() )
                .applyComment( getApplyComment() )
                .applyStatus( BaseCodeEnum.codeOf( ApprovalResult.class, getApplyStatus()).toString() )
                .applyText(getApplyText())
                .applyAccessory(getApplyAccessory())
                .approver(getApprover())
                .semester( BaseCodeEnum.codeOf(SemesterEnum.class, getSemester()).toString() )
                .build();
    }

}
