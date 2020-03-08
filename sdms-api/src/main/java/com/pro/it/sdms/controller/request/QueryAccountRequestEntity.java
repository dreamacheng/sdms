package com.pro.it.sdms.controller.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.pro.it.common.utils.PageInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QueryAccountRequestEntity {

    @JsonProperty("pageInfo")
    private PageInfo pageInfo;

    /** 姓名 */
    private String username;

    /** 用户编号（学号、职工号） */
    private String accountNo;

    /** 在校公寓 */
    private String lodgingHouse;

    /** 专业科系 */
    private String department;

    /** 政治面貌 */
    private String politicsStatus;

    /** 用户角色 */
    private String role;

}
