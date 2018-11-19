package com.huya.tool.dto;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendRespDataDto {
    private String sessionData;
    private Integer isWhiteListRegist;
}
