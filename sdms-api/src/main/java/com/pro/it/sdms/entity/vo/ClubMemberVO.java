package com.pro.it.sdms.entity.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder(toBuilder=true)
public class ClubMemberVO {

    /** 社员 */
    private AccountVO member;

    /** 社团*/
    private SchoolClubVO club;

    /** 活跃度 */
    private String liveness;

    /** 职位*/
    private String position;

}
