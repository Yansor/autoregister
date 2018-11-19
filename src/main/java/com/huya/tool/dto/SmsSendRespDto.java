package com.huya.tool.dto;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendRespDto {
    private String uri; //60004
    private String version;
    private String context;
    private Integer requestId;
    private Integer returnCode; //2003
    private String message; //MOBILE_HAS_REGISTER
    private String description; //手机号码已注册
    private SmsSendRespDataDto data;
}
