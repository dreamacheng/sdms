package com.pro.it.sdms.entity.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "sdms_school_club")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
/**
 * 社团表
 */
public class SchoolClub {

    /** 社团名称 */
    @Column(name = "name", columnDefinition = "varchar(30)")
    private String name;

    /** 现任社长 */
    @OneToOne
    @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "account_id")
    private Account leader;

    /** 社团简介 */
    @Column(name = "apply_accessory", columnDefinition = "varchar(100)")
    private String introduction;

}
