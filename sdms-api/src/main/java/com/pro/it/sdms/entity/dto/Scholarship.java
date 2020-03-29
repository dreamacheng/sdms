package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.ScholarshipVO;
import com.pro.it.sdms.enums.ApprovalResult;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.MoneyLevelEnum;
import com.pro.it.sdms.enums.SemesterEnum;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "sdms_scholarship")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Scholarship extends BaseDTO {

    /** 申请类型 */
    @Column(name = "level", nullable = false, columnDefinition = "int")
    private short level;

    /** 申请学年 */
    @Column(name = "term", nullable = false, columnDefinition = "int")
    private short term;

    /** 申请学生 */
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="student_id")
    private Account winner;

    /** 审核状态 */
    @Column(name = "status",nullable = false, columnDefinition = "int")
    private short status;

    /** 审核单位 */
    @Column(name = "approver", columnDefinition = "varchar(30)")
    private String approver;

    @Column(name = "apply_comment", columnDefinition = "varchar(300)")
    private String applyComment;

    /** 申请文 */
    @Column(name = "apply_text", nullable = false, columnDefinition = "text")
    private String applyText;

    public ScholarshipVO toVO() {
        return ScholarshipVO.builder()
                .applyTime(getCreateDatetime())
                .level(BaseCodeEnum.codeOf(MoneyLevelEnum.class, getLevel()).toString())
                .studentName(getWinner().getUsername())
                .studentNo(getWinner().getAccountNo())
                .applyComment(getApplyComment())
                .applyTime(getCreateDatetime())
                .id(getId())
                .status(BaseCodeEnum.codeOf(ApprovalResult.class, getStatus()).toString())
                .applyText(getApplyText())
                .term(BaseCodeEnum.codeOf(SemesterEnum.class, getTerm()).toString())
                .approver(getApprover()).build();
    }

}
