package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.CertificateVO;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "sdms_certificate")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Certificate extends BaseDTO {

    @Column(name = "name", columnDefinition = "varchar(100)")
    private String name;

    @Column(name = "grade", columnDefinition = "varchar(300)")
    private String grade;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="account_id")
    private Account account;

    public CertificateVO toVO() {
        return CertificateVO.builder()
                .grade(getGrade())
                .name(getName())
                .student(account.toVO()).build();
    }

}
