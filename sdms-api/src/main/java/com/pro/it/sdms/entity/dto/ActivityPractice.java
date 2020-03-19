package com.pro.it.sdms.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.util.Date;

/**
 * 社会实践表
 */
@Entity
@Table(name = "sdms_activity_practice")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
public class ActivityPractice {

    /** 发起组织 */
    @Column(name = "organization", columnDefinition = "varchar(100)")
    private String organization;

    /** 活动主题 */
    @Column(name = "title", columnDefinition = "varchar(100)")
    private String title;

    /** 活动内容 */
    @Column(name = "content", columnDefinition = "varchar(1000)")
    private String content;

    /** 开始时间 */
    @Column(name = "start_time", columnDefinition = "datetime")
    private Date startTime;

    /** 结束时间 */
    @Column(name = "end_time", columnDefinition = "datetime")
    private Date endTime;

}
