package com.pro.it.sdms.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pro.it.sdms.entity.vo.OrganizationApplyVO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrganizationListResponseEntity {

    private List<OrganizationApplyVO> notList;

    private List<OrganizationApplyVO> list;
}
