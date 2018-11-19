package com.huya.tool.dto;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDataReqDto {
    private String behavior;
    private String domainList;
    private String page;
    private String password;
    private String phone;
    private String sessionData;
    private String smsCode;
}
