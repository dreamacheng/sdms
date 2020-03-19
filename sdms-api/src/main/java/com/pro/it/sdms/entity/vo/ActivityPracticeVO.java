package com.pro.it.sdms.entity.vo;

import com.pro.it.sdms.entity.dto.ActivityPractice;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.util.Date;

@Setter
@Getter
@ToString
@Builder(toBuilder=true)
public class ActivityPracticeVO {

    /** 发起组织 */
    private String organization;

    /** 活动主题 */
    private String title;

    /** 活动内容 */
    private String content;

    /** 开始时间 */
    private Date startTime;

    /** 结束时间 */
    private Date endTime;

    public ActivityPractice toDTO() {
        return ActivityPractice.builder()
                .organization(getOrganization())
                .title(getTitle())
                .content(getContent())
                .startTime(getStartTime())
                .endTime(getEndTime()).build();
    }
}
