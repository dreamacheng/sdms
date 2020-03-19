package com.pro.it.sdms.entity.dto;


import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.SchoolClubVO;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "sdms_school_club")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder
/**
 * 社团表
 */
public class SchoolClub extends BaseDTO {

    /** 社团名称 */
    @Column(name = "name", columnDefinition = "varchar(30)")
    private String name;

    /** 现任社长 */
    @Column(name = "leader_no", columnDefinition = "varchar(30)")
    private String leader;

    /** 社团简介 */
    @Column(name = "apply_accessory", columnDefinition = "varchar(100)")
    private String introduction;

    public SchoolClubVO toVO() {
        return SchoolClubVO.builder()
                .introduction(getIntroduction())
                .createTime(getCreateDatetime())
                .leader(getLeader())
                .name(getName()).build();
    }

}
