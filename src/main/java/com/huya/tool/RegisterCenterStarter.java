package com.huya.tool;

import com.huya.tool.messagePlatform.YouMaMobileCode;
import com.huya.tool.messagePlatform.YouMaMobileConfig;
import com.huya.tool.util.StringUtils;

public class RegisterCenterStarter {
    public static void main(String[] args) {
//        String phone = "13951635674";
        for(int i = 0 ; i<50 ; i++){
            String passwrod = "abc895686843";

            //获取手机号码
            YouMaMobileCode messageUtil = new YouMaMobileCode(YouMaMobileConfig.AUTHOR_UID
                    ,YouMaMobileConfig.AUTHOR_PASSWD
                    ,YouMaMobileConfig.PID);

            messageUtil.LoginIn();
            String phone = messageUtil.GetMobilenum();

            if (StringUtils.isNotEmpty(phone)){
                RegisterCenter rc = new RegisterCenter(phone , passwrod);
                rc.run();
            }
        }


    }
}
