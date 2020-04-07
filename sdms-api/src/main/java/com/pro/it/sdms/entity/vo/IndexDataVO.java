package com.pro.it.sdms.entity.vo;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder=true)
public class IndexDataVO {

    private String title;

    private String link;

    private String date;

}
