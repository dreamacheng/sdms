package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.ActivityResultVO;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "sdms_activity_join")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ActivityResult extends BaseDTO {

    /** 参与者 */
    @OneToOne()
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account member;

    /** 活动*/
    @OneToOne()
    @JoinColumn(name = "activity_id", referencedColumnName = "id")
    private ActivityPractice activityPractice;

    /** 活动总结 */
    @Column(name = "summary", columnDefinition = "text")
    private String summary;

    public ActivityResultVO toVO() {
        return ActivityResultVO.builder()
                .id(getId())
                .accountVO(member.toVO())
                .activityPracticeVO(activityPractice.toVO())
                .summary(getSummary()).build();
    }

}
