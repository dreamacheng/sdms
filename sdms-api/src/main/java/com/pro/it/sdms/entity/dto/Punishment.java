package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.PunishmentVO;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.PunishmentTypeEnum;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sdms_punishment")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Punishment extends BaseDTO {

    /** 处分类型 */
    @Column(name = "type", nullable = false, columnDefinition = "int")
    private short type;

    /** 处分时间 */
    @Column(name = "punishment_time", nullable = false, columnDefinition = "datetime")
    private Date punishmentTime;

    /** 取消时间 */
    @Column(name = "cancel_time", nullable = false, columnDefinition = "datetime")
    private Date cancelTime;

    /** 是否取消 */
    @Column(name = "is_cancel", nullable = false, columnDefinition = "int")
    private Short isCancel;

    /** 受处分人 */
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="student_id")
    private Account student;

    /** 处分原因及描述 */
    @Column(name = "description", columnDefinition = "varchar(255)")
    private String desc;

    public PunishmentVO toVO() {
        return PunishmentVO.builder()
                .punishmentNo(getStudent().getAccountNo())
                .punishmentName(getStudent().getUsername())
                .cancelTime(getCancelTime())
                .desc(getDesc())
                .punishmentTime(getPunishmentTime())
                .isCancel(getIsCancel())
                .type(BaseCodeEnum.codeOf(PunishmentTypeEnum.class, getType()).toString()).build();
    }

}
