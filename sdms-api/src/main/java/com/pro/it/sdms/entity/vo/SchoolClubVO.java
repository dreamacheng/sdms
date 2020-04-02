package com.pro.it.sdms.entity.vo;

import com.pro.it.sdms.entity.dto.ClubApply;
import com.pro.it.sdms.entity.dto.SchoolClub;
import com.pro.it.sdms.enums.ApprovalResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ToString
@Builder(toBuilder=true)
@Getter
@Setter
public class SchoolClubVO {

    /** 社团Id */
    private BigDecimal id;

    /** 社团名称 */
    private String name;

    /** 现任社长 */
    private String leader;

    /** 社团简介 */
    private String introduction;

    /** 社团类型 */
    private String type;

    private String logoUrl;

    /** 社团人数 */
    private Integer number;

    /** 创建时间 */
    private Date createTime;

    public ClubApply toDTO() {
        return ClubApply.builder()
                .introduction(getIntroduction())
                .type(getType())
                .logoUrl(getLogoUrl())
                .applyStatus(ApprovalResult.WaitForApproval.getCode())
                .name(getName()).build();
    }
}
