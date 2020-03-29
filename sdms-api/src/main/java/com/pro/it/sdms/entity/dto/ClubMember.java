package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.ClubMemberVO;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.ClubLivenessEnum;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "sdms_club_member")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClubMember extends BaseDTO {

    /** 社员 */
    @OneToOne()
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account member;

    /** 社团*/
    @OneToOne()
    @JoinColumn(name = "club_id", referencedColumnName = "id")
    private SchoolClub club;

    /** 活跃度 */
    @Column(name = "politics_status", nullable = false, columnDefinition = "int")
    private short liveness;

    /** 职位*/
    @Column(name = "identity_card", nullable = false, columnDefinition = "varchar(30)")
    private String position;

    /** 是否加入 (1:加入，0：未加入)*/
    @Column(name = "member_status", nullable = false, columnDefinition = "int")
    private short memberStatus;

    public ClubMemberVO toVO() {
        return ClubMemberVO.builder()
                .id(getId())
                .club(getClub().toVO())
                .member(getMember().toVO())
                .liveness(BaseCodeEnum.codeOf(ClubLivenessEnum.class, getLiveness()).toString())
                .position(getPosition()).build();
    }

}
