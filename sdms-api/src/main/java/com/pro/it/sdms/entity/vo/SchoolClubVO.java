package com.pro.it.sdms.entity.vo;

import com.pro.it.sdms.entity.dto.SchoolClub;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.util.Date;

@ToString
@Builder(toBuilder=true)
@Getter
@Setter
public class SchoolClubVO {

    /** 社团名称 */
    private String name;

    /** 现任社长 */
    private String leader;

    /** 社团简介 */
    private String introduction;

    /** 创建时间 */
    private Date createTime;

    public SchoolClub toDTO() {
        return SchoolClub.builder()
                .introduction(getIntroduction())
                .leader(getLeader())
                .name(getName()).build();
    }
}
