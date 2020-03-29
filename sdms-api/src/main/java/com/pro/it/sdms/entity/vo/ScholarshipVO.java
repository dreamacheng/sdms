package com.pro.it.sdms.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.Scholarship;
import com.pro.it.sdms.enums.ApprovalResult;
import com.pro.it.sdms.enums.MoneyLevelEnum;
import com.pro.it.sdms.enums.SemesterEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@ToString
@Builder(toBuilder=true)
@Getter
@Setter
public class ScholarshipVO {

    private BigDecimal id;

    /** 申请类型 */
    private String level;

    /** 申请学期 */
    private String term;

    /** 申请人学号 */
    private String studentNo;

    /** 申请人姓名 */
    private String studentName;

    /** 审核状态 */
    private String status;

    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT")
    private Date applyTime;

    /** 审批人 */
    private String approver;

    /** 申请文 */
    private String applyText;

    /** 审批意见 */
    private String applyComment;

    public Scholarship toDTO() {
        return Scholarship.builder()
                .level(MoneyLevelEnum.valueOf(getLevel()).getCode())
                .term(SemesterEnum.valueOf(term).getCode())
                .applyComment(getApplyComment())
                .applyText(getApplyText())
                .status(ApprovalResult.valueOf(status).getCode()).build();
    }
}
