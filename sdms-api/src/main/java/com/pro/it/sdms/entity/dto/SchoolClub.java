package com.pro.it.sdms.entity.dto;


import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.SchoolClubVO;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "sdms_school_club")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
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

    /** 社团logo */
    @Column(name = "logo_url", columnDefinition = "varchar(300)")
    private String logoUrl;

    /** 社团类型 */
    @Column(name = "type", columnDefinition = "varchar(30)")
    private String type;

    /** 社团简介 */
    @Column(name = "introduction", columnDefinition = "varchar(100)")
    private String introduction;

    @Column(name = "number", columnDefinition = "int")
    private Integer number;

    public SchoolClubVO toVO() {
        return SchoolClubVO.builder()
                .id(getId())
                .introduction(getIntroduction())
                .createTime(getCreateDatetime())
                .leader(getLeader())
                .type(getType())
                .number(getNumber())
                .logoUrl(getLogoUrl())
                .name(getName()).build();
    }

}
