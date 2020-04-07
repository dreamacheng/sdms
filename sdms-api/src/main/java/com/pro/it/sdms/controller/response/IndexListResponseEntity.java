package com.pro.it.sdms.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pro.it.sdms.entity.vo.IndexDataVO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IndexListResponseEntity {

    private List<IndexDataVO> schoolNews;

    private List<IndexDataVO> notices;

    private List<IndexDataVO> mediaCollege;

    private List<IndexDataVO> basicInfo;

    private List<IndexDataVO> academic;
}
