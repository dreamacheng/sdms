package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sdms_account_info")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
public class AccountInfo extends BaseDTO {


    /** 学院 **/
    @Column(name = "college", columnDefinition = "varchar(30)")
    private String college;

    /** 专业科系 */
    @Column(name = "major", columnDefinition = "varchar(30)")
    private String major;

    /** 在校公寓 */
    @Column(name = "lodging_house", columnDefinition = "varchar(30)")
    private String lodgingHouse;

    /** 入学时间 */
    @Column(name = "enrollment", columnDefinition = "date")
    private Date enrollment;

    @ManyToMany
    @JoinTable(name = "accountInfo_schoolClub",joinColumns = @JoinColumn(name = "accountInfo_id"),
            inverseJoinColumns = @JoinColumn(name = "schoolClub_id"))
    private List<SchoolClub> schoolClubList;

//    @OneToMany
//    private List<SemesterEvaluation> evaluationList;

}
