package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.PunishmentVO;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
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
    @Column(name = "type", nullable = false, columnDefinition = "varchar(30)")
    private String type;

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
    @Column(name = "student_no", nullable = false, columnDefinition = "varchar(30)")
    private String studentNo;

    /** 受处分人姓名 */
    @Column(name = "student_name", nullable = false, columnDefinition = "varchar(30)")
    private String studentName;

    /** 处分原因及描述 */
    @Column(name = "description", columnDefinition = "varchar(255)")
    private String desc;

    public PunishmentVO toVO() {
        return PunishmentVO.builder()
                .punishmentNo(getStudentNo())
                .punishmentName(getStudentName())
                .cancelTime(getCancelTime())
                .desc(getDesc())
                .punishmentTime(getPunishmentTime())
                .isCancel(getIsCancel())
                .type(getType()).build();
    }

}
