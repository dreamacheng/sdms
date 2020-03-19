package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sdms_account_info")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
public class AccountInfo extends BaseDTO {


    /** 学院 **/
    @Column(name = "college", nullable = false, columnDefinition = "varchar(30)")
    private String college;

    /** 专业科系 */
    @Column(name = "major", nullable = false, columnDefinition = "varchar(30)")
    private String major;

    /** 政治面貌 */
    @Column(name = "politics_status", nullable = false, columnDefinition = "int")
    private Short politicsStatus;

    /** 在校公寓 */
    @Column(name = "lodging_house", nullable = false, columnDefinition = "varchar(30)")
    private String lodgingHouse;

    /** 入学时间 */
    @Column(name = "enrollment", nullable = false, columnDefinition = "datetime")
    private Date enrollment;

}
