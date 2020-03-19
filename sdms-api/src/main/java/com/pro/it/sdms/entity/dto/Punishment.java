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
@Builder
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
    @Column(name = "apply_text", nullable = false, columnDefinition = "int")
    private Short isCance;

    /** 受处分人 */
    @Column(name = "account_no", nullable = false, columnDefinition = "varchar(30)")
    private String accountNo;

    /** 处分原因及描述 */
    @Column(name = "apply_text", columnDefinition = "varchar(1000)")
    private String desc;

    public PunishmentVO toVO() {
        return PunishmentVO.builder().punishmentNo(getAccountNo())
                .cancelTime(getCancelTime())
                .desc(getDesc())
                .punishmentTime(getPunishmentTime())
                .isCance(getIsCance())
                .type(getType()).build();
    }

}
