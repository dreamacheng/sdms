package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.ClubApplyVO;
import com.pro.it.sdms.enums.ApprovalResult;
import com.pro.it.sdms.enums.BaseCodeEnum;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name = "sdms_club_apply")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClubApply extends BaseDTO {

    /** 社团名称 */
    @Column(name = "name", nullable = false, columnDefinition = "varchar(30)")
    private String name;

    /** 社团logo */
    @Column(name = "logo_url", nullable = false, columnDefinition = "varchar(300)")
    private String logoUrl;

    /** 社团类型 */
    @Column(name = "type", nullable = false, columnDefinition = "varchar(30)")
    private String type;

    /** 社团简介 */
    @Column(name = "introduction", nullable = false, columnDefinition = "varchar(100)")
    private String introduction;

    @Column(name = "applyStatus", nullable = false, columnDefinition = "int")
    private short applyStatus;

    @Column(name = "approver", columnDefinition = "varchar(30)")
    private String approver;

    @Column(name = "approval_comment", columnDefinition = "varchar(100)")
    private String approvalComment;

    public ClubApplyVO toVO() {
        return ClubApplyVO.builder()
                .applyStatus(BaseCodeEnum.codeOf(ApprovalResult.class, getApplyStatus()).toString())
                .logoUrl(getLogoUrl())
                .introduction(getIntroduction())
                .type(getType())
                .approver(getApprover())
                .approvalComment(getApprovalComment())
                .name(getName())
                .build();
    }

}
