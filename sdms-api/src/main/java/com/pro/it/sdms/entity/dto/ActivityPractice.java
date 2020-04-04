package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.ActivityPracticeVO;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "sdms_activity_practice")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder(toBuilder=true)
@AllArgsConstructor
/**
 * 社会实践表
 */
public class ActivityPractice extends BaseDTO {

    /** 发起组织 */
    @Column(name = "organization",nullable = false, columnDefinition = "varchar(100)")
    private String organization;

    /** 活动主题 */
    @Column(name = "title",nullable = false,  columnDefinition = "varchar(100)")
    private String title;

    /** 活动内容 */
    @Column(name = "content",nullable = false,  columnDefinition = "varchar(1000)")
    private String content;

    /** 报名开始时间 */
    @Column(name = "apply_start_time",nullable = false,  columnDefinition = "datetime")
    private Date applyStartTime;

    /** 报名结束时间 */
    @Column(name = "apply_end_time",nullable = false,  columnDefinition = "datetime")
    private Date applyEndTime;

    /** 开始时间 */
    @Column(name = "start_time",nullable = false,  columnDefinition = "datetime")
    private Date startTime;

    /** 结束时间 */
    @Column(name = "end_time",nullable = false,  columnDefinition = "datetime")
    private Date endTime;

    /** 参加人员 */
    @ManyToMany
    @JoinTable(name = "activity_account",joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"))
    private List<Account> attendeeList;

    public ActivityPracticeVO toVO() {
        String type;
        Date now = new Date();
        if (now.before(applyStartTime)) {
            type = "准备报名";
        } else if (now.after(applyStartTime) && now.before(applyEndTime)) {
            type = "开始报名";
        } else if (now.after(applyEndTime) && now.before(startTime)){
            type = "报名结束";
        } else if (now.after(startTime) && now.before(endTime)) {
            type = "活动开始";
        } else {
            type = "活动结束";
        }
        if (attendeeList == null) {
            attendeeList = Collections.EMPTY_LIST;
        }
        return ActivityPracticeVO.builder()
                .id(getId())
                .organization(getOrganization())
                .type(type)
                .title(getTitle())
                .content(getContent())
                .applyStartTime(getApplyStartTime())
                .applyEndTime(getEndTime())
                .startTime(getStartTime())
                .attendeeList(getAttendeeList().stream().map(Account::toVO).collect(Collectors.toList()))
                .endTime(getEndTime()).build();
    }

}
