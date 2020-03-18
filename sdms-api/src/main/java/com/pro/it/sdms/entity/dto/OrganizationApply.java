package com.pro.it.sdms.entity.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "sdms_organization_apply")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
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

    /** 审批状态 */
    @Column(name = "apply_status", nullable = false, columnDefinition = "int")
    private Short applyStatus;

    /** 审批意见 */
    @Column(name = "apply_comment", nullable = false, columnDefinition = "varchar(300)")
    private String applyComment;

}
