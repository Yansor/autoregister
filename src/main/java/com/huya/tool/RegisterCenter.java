package com.huya.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huya.tool.dto.HasRegisterDataReqDto;
import com.huya.tool.dto.HasRegisterResDto;
import com.huya.tool.dto.RegisterDataReqDto;
import com.huya.tool.dto.SmsSendRespDto;
import com.huya.tool.messagePlatform.YouMaMobileCode;
import com.huya.tool.messagePlatform.YouMaMobileConfig;
import com.huya.tool.util.HttpClientUtil;
import lombok.Builder;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.nio.charset.Charset;
import java.util.HashMap;

import lombok.*;

public class RegisterCenter {

    private CookieStore cookieStore = new BasicCookieStore();

    private HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    private String phone;
    private String password;
    private String smsCode;
    private SmsSendRespDto smsSendRespDto;

    public RegisterCenter(String phone, String passwd){
        this.phone = phone;
        this.password = passwd;
    }

    /**
     *  手机号码是否虎牙已注册 ，此接口不准,已废弃 ，请参考smsSend方法
     * @param phoneNum
     * @return
     */
    @Deprecated
    private Boolean hasRegister(String phoneNum) {
        String mobileIsRegistUrl = "https://udbreg.huya.com/web/v2/mobileIsRegist";

        HttpPost mobileIsRegistPost = new HttpPost(mobileIsRegistUrl);
        mobileIsRegistPost.addHeader(new BasicHeader("Content-Type" , "application/json;charset=UTF-8"));
        mobileIsRegistPost.addHeader(new BasicHeader("Referer" , "https://udbreg.huya.com/web/middle/2.2/51458388/https"));
        mobileIsRegistPost.addHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:63.0) Gecko/20100101 Firefox/63.0"));

        HashMap<String,Object> postParam = new HashMap<>();
        postParam.put("appId","5002");
        postParam.put("byPass","3");
        postParam.put("context","WB-70764d87acb14c988da0124eb18fc760-C83800FBD5B00001CC8D3E281619E290-");
        postParam.put("data", HasRegisterDataReqDto.builder().phone("086"+phoneNum).build());
        postParam.put("lcid","2052");
        postParam.put("requestId","51625564");
        postParam.put("sdid","51458388");
        postParam.put("url","60015");
        postParam.put("version","2.2");

        String postJson = JSON.toJSONString(postParam);
        System.out.println();
        mobileIsRegistPost.setEntity(new StringEntity(postJson , Charset.defaultCharset()));
        String hasRegStr = HttpClientUtil.getOrPost(mobileIsRegistPost , client);

        HasRegisterResDto hasRegisterDto = JSONObject.parseObject(hasRegStr , HasRegisterResDto.class);

        return hasRegisterDto.getData().getRegisted();
    }

    public boolean smsSend(){
        String smsSendUrl = "https://udbreg.huya.com/sms/v2/send/reg";

        HttpPost smsSendPost = new HttpPost(smsSendUrl);

        smsSendPost.addHeader(new BasicHeader("Content-Type" , "application/json;charset=UTF-8"));
        smsSendPost.addHeader(new BasicHeader("Referer" , "https://udbreg.huya.com/web/middle/2.2/60045581/https"));
        smsSendPost.addHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:63.0) Gecko/20100101 Firefox/63.0"));

        HashMap<String,Object> postParam = new HashMap<>();
        postParam.put("appId","5002");
        postParam.put("byPass","3");
        postParam.put("context","WB-70764d87acb14c988da0124eb18fc760-C83800FBD5B00001CC8D3E281619E290-");
        postParam.put("data", HasRegisterDataReqDto.builder().phone("086"+phone).build());
        postParam.put("lcid","2052");
        postParam.put("requestId","60160938");
        postParam.put("sdid","60045581");
        postParam.put("uri","60003");
        postParam.put("version","2.2");

        String postJson = JSON.toJSONString(postParam);
        System.out.println(postJson);
        smsSendPost.setEntity(new StringEntity(postJson , Charset.defaultCharset()));

        String smsSendResp = HttpClientUtil.getOrPost(smsSendPost , client);

        SmsSendRespDto smsSendRespDto = JSONObject.parseObject(smsSendResp , SmsSendRespDto.class);

        this.smsSendRespDto = smsSendRespDto;
        System.out.println(smsSendRespDto.getDescription());
        if(smsSendRespDto.getReturnCode().intValue() == 2003){
            //手机号码已注册
            return true;
        }
       return false;
    }


    public void run(){
        //是否已注册
        Boolean hasRegistered = smsSend();
        if(!hasRegistered){
            //触发验证码发送按钮
//            send();
            //获取验证码
            getCheckCode();


            //注册
            doRegister();

            //访问个人中心 读取虎牙号
            getMemberCenter();
            //注销
            logout();
        }


    }

    private void getMemberCenter() {
        String memberCenterUrl = "https://i.huya.com/";
        HttpGet memberCenterGet = new HttpGet(memberCenterUrl);
    }

//    private void send() {
//        String sendUrl = "https://udbreg.huya.com/sms/v2/send/reg";
//
//        HttpPost sendPost = new HttpPost(sendUrl);
//
//        sendPost.addHeader(new BasicHeader("Content-Type" , "application/json;charset=UTF-8"));
//        sendPost.addHeader(new BasicHeader("Referer" , "https://udbreg.huya.com/web/middle/2.2/67951871/https"));
//        sendPost.addHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:63.0) Gecko/20100101 Firefox/63.0"));
//
//        HashMap<String,Object> postParam = new HashMap<>();
//        postParam.put("appId","5002");
//        postParam.put("byPass","3");
//        postParam.put("context","WB-70764d87acb14c988da0124eb18fc760-C83800FBD5B00001CC8D3E281619E290-");
//        postParam.put("data", HasRegisterDataReqDto.builder().phone("086"+phone).build());
//        postParam.put("lcid","2052");
//        postParam.put("requestId","68051829");
//        postParam.put("sdid","67951871");
//        postParam.put("uri","60003");
//        postParam.put("version","2.2");
//
//        String postJson = JSON.toJSONString(postParam);
//        System.out.println(postJson);
//        sendPost.setEntity(new StringEntity(postJson , Charset.defaultCharset()));
//
//        String sendCallResp = HttpClientUtil.getOrPost(sendPost , client);
//
//
//        System.out.println(sendCallResp);
//    }

    private void getCheckCode() {

        YouMaMobileCode messageUtil = new YouMaMobileCode(
                YouMaMobileConfig.AUTHOR_UID,
                YouMaMobileConfig.AUTHOR_PASSWD,
                YouMaMobileConfig.PID
        );
        //第三方手机接码平台登录
        String loginResp = messageUtil.LoginIn();

        //第三方手机接码平台获取号码
        do{
            try {
                //延时5秒取验证码
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            smsCode = messageUtil.getVcodeAndReleaseMobile(phone);
        }while(smsCode.equals("no_data"));

        //reg expression match check code
        smsCode = smsCode.substring(smsCode.indexOf("：") +1,  smsCode.indexOf("，"));


        return;


    }



    private void doRegister() {
        String regUrl = "https://udbreg.huya.com/web/v2/mobileReg";
        HttpPost regPost = new HttpPost(regUrl);

        regPost.addHeader(new BasicHeader("Content-Type" , "application/json;charset=UTF-8"));
        regPost.addHeader(new BasicHeader("Referer" , "https://udbreg.huya.com/web/middle/2.2/51458388/https"));
        regPost.addHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:63.0) Gecko/20100101 Firefox/63.0"));



        HashMap<String,Object> postParam = new HashMap<>();
        postParam.put("appId","5002");
        postParam.put("byPass","3");
        postParam.put("context","WB-70764d87acb14c988da0124eb18fc760-C83800FBD5B00001CC8D3E281619E290-");
        postParam.put("data", RegisterDataReqDto.builder()
                .behavior("%5B%7B%22page.register%22%3A0.114%7D%2C%7B%22input.r.phone%22%3A0.256%7D%2C%7B%22" +
                        "input.r.phone%22%3A90.113%7D%2C%7B%22" +
                        "button.UDBSdkRegister.code%22%3A95.628%7D%2C%7B%22" +
                        "button.UDBSdkRegister.code%22%3A96.602%7D%2C%7B%22" +
                        "input.r.code%22%3A96.814%7D%2C%7B%22input.r.phone%22%3A98.578%7D%2C%7B%22" +
                        "button.UDBSdkRegister.code%22%3A105.121%7D%2C%7B%22input.r.code%22%3A105.577%7D%2C%7B%22" +
                        "input.r.passwd%22%3A122.622%7D%2C%7B%22button.UDBSdkRegister%22%3A167.185%7D%5D")
                .domainList(null)
                .page("https%3A%2F%2Fwww.huya.com%2F")
                .password(password)
                .phone("086"+phone)
                .sessionData(smsSendRespDto.getData().getSessionData())
                .smsCode(smsCode)
                .build());
        postParam.put("lcid","2052");
        postParam.put("requestId",smsSendRespDto.getRequestId());  //变了
        postParam.put("sdid","51458388");
        //postParam.put("url","60015"); //变了
        postParam.put("uri",smsSendRespDto.getUri());
        postParam.put("version",smsSendRespDto.getVersion());

        String postJson = JSON.toJSONString(postParam);
        System.out.println(postJson);
        regPost.setEntity(new StringEntity(postJson , Charset.defaultCharset()));


        String regResp = HttpClientUtil.getOrPost(regPost , client);
        System.out.println(regResp);
        return;

    }

    public void logout(){
        udblgnDomainLogout();
        huyaDomainLogout();
    }

    private void huyaDomainLogout() {
        String huyaLogoutUrl = "https://www.huya.com/udb_web/udbport2.php?m=HuyaHome&do=huyaLogout";

        HttpGet huyaLogoutGet = new HttpGet(huyaLogoutUrl);
        huyaLogoutGet.setHeader(new BasicHeader("Referer" , "https://www.huya.com/"));
        huyaLogoutGet.setHeader(new BasicHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:63.0) Gecko/20100101 Firefox/63.0"));

        String huyaLogoutResp = HttpClientUtil.getOrPost(huyaLogoutGet , client);
        System.out.println(huyaLogoutResp);
    }

    private void udblgnDomainLogout() {
        String udbLogoutUrl = "https://udblgn.huya.com/web/v2/logout?58900291";

        HttpGet udbLogoutGet = new HttpGet(udbLogoutUrl);
        udbLogoutGet.setHeader(new BasicHeader("Referer" , "https://www.huya.com/"));
        udbLogoutGet.setHeader(new BasicHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:63.0) Gecko/20100101 Firefox/63.0"));

        String udbLogoutResp = HttpClientUtil.getOrPost(udbLogoutGet , client);
        System.out.println(udbLogoutResp);
    }


}
