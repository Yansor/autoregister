package com.huya.tool.dto;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HasRegisterDataRespDto {
    private Integer isWhiteListRegist;
    private Boolean registed;
}
