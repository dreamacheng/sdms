package com.pro.it.sdms.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pro.it.sdms.entity.vo.ScholarshipVO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScholarshipListResponseEntity {

    private List<ScholarshipVO> notList;

    private List<ScholarshipVO> list;
}
