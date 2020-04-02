package com.pro.it.sdms.entity.vo;

import lombok.*;

@ToString
@Getter
@Setter
@Builder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
public class ClubApplyVO {

    /** 社团名称 */
    private String name;

    /** 社团logo */
    private String logoUrl;

    /** 社团类型 */
    private String type;

    /** 社团简介 */
    private String introduction;

    private String creator;

    /** 审核状态 */
    private String applyStatus;

    /** 审核老师 */
    private String approver;

    /** 审批意见 */
    private String approvalComment;
}
