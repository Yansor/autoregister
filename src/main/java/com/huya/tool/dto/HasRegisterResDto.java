package com.huya.tool.dto;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HasRegisterResDto {

    private String uri;
    private String version;
    private String context;
    private Integer requestId;
    private Integer returnCode;
    private String message;
    private String description;
    private HasRegisterDataRespDto data;



}
