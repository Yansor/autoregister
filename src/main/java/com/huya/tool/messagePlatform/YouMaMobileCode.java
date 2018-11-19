package com.huya.tool.messagePlatform;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/*
 * @site http://www.6tudou.com:9000/download.html 官方网址
 */
public class YouMaMobileCode {
    //橙子平台登陆
    final static String LOGININURL = "http://www.517orange.com:9000/DevApi/loginIn";

    //获取手机号码
    final static String GETMOBILENUMURL = "http://www.517orange.com:9000/DevApi/getMobilenum";

    //获取验证码并释放
    final static String GETVCODEANDRELEASEMOBILEURL = "http://www.517orange.com:9000/DevApi/getVcodeAndReleaseMobile";

    //获取验证码并释放
    final static String ADDIGNORELISTURL = "http://www.517orange.com:9000/DevApi/addIgnoreList";

    //获取该用户所使用的号码和项目
    final static String GETRECVINGINFO = "http://www.517orange.com:9000/DevApi/getRecvingInfo";

    //    public static readonly String LoginIn = "http://www.517orange.com:9000/DevApi/loginIn";//登陆
    //    public static readonly String GetMobilenum = "http://www.517orange.com:9000/DevApi/getMobilenum";//获取号码
    //    public static readonly String GetVcodeAndHoldMobilenum = "http://www.517orange.com:9000/DevApi/getVcodeAndHoldMobilenum";//获取短信并继续使用该号码
    //    public static readonly String GetVcodeAndReleaseMobile = "http://www.517orange.com:9000/DevApi/getVcodeAndReleaseMobile";//获取短信并释放该号码
    //    public static readonly String AddIgnoreList = "http://www.517orange.com:9000/DevApi/addIgnoreList";//添加黑名单
    //    public static readonly String GetRecvingInfo = "http://www.517orange.com:9000/DevApi/getRecvingInfo";//获取该用户所使用的号码和项目
    //    public static readonly String CancelSMSRecvAll = "http://www.517orange.com:9000/DevApi/cancelSMSRecvAll";//释放所有号码
    //    public static readonly String CancelSMSRecv = "http://www.517orange.com:9000/DevApi/cancelSMSRecv";//释放单个号码


    String pid;

    String uid;

    String author_uid;

    String author_pwd;

    String token;

    public YouMaMobileCode(String author_uid, String author_pwd , String pid) {
        super();
        this.pid = pid;
        this.author_uid = author_uid;
        this.author_pwd = author_pwd;
    }

    public static void main(String[] args) {
        String pid = "6168";//12306项目id
        String uid = "";//用户id
        String author_uid = "";//开发者用户名
        String author_pwd = "";//开发者用户名
        String token = "";
        YouMaMobileCode youmamobilecode = new YouMaMobileCode(author_uid, author_pwd  , pid);
        String result = "";
        result = youmamobilecode.LoginIn();
        //        System.out.println(result);
        token = youmamobilecode.gettoken(result, "Token");
        youmamobilecode.setToken(token);
        String Uid = youmamobilecode.gettoken(result, "Uid");
        String mobile = "";
        if (!"-1".equals(token)) {
                        mobile = youmamobilecode.GetMobilenum();
                        System.out.println(result);
        }
//                获取验证码并释放
                result = youmamobilecode.getVcodeAndReleaseMobile("");
//        添加黑名单
                result = youmamobilecode.AddIgnoreList("");
//        获取当前用户正在使用的号码列表
                result = youmamobilecode.GetRecvingInfo();
        System.out.println(result);
    }

    /**
     * 从json里获取到token
     * @param result
     * @return
     * @time 2015年7月29日 下午3:35:09
     * @author chendong
     */
    public String gettoken(String result, String key) {
        JSONObject jsonObject = JSONObject.parseObject(result);
        String Token = "-1";
        try {
            Token = jsonObject.getString(key);
        }
        catch (Exception e) {
        }
        return Token;
    }

    /**
     * 橙子平台登陆
     * @param
     * @param
     * @return
     * @time 2018年7月29日 下午6:04:06
     * @author chendong
     */
    public String LoginIn() {
        if (author_uid == null || author_pwd == null) {
            return "用户名或密码不能为空";
        }
        else {
            String paramContent = "uid=" + author_uid + "&pwd=" + author_pwd;
            //            System.out.println(LOGININURL);
            //            System.out.println(paramContent);
            //            {"Uid":120865,"Token":"31RroGrAw5Var/mSNgs6BlSbPonIBFVeK8iNDhAgrA5nDBtGQmyoYtc0qApnKJl%2Bvyv%2BQXO/js0=","InCome":0.000,"Balance":4.900,"UsedMax":20}
            String result = submitPost(LOGININURL, paramContent, "utf-8").toString();// .Post(API.LoginIn, String.Format("uid={0}&pwd={1}", uid, pwd));
            //            System.out.println(result);

            this.token = gettoken(result,  "Token");
            this.uid = gettoken(result , "Uid");
            return result;
        }
    }

    /**
     * 获取手机号码
     * @time 2018年7月29日 下午3:46:44
     * @author chendong
     */
    public String GetMobilenum() {
        if (uid == null || pid == null || token == null) {
            return "用户名或密码不能为空";
        }
        else {
            String paramContent = "uid=" + uid + "&pid=" + pid + "&token=" + token;
            System.out.println(GETMOBILENUMURL);
            System.out.println(paramContent);
            String result = submitPost(GETMOBILENUMURL, paramContent, "utf-8").toString();// .Post(API.LoginIn, String.Format("uid={0}&pwd={1}", uid, pwd));
            System.out.println(result);
            return result;
        }
    }

    /**
     * 获取验证码并保留
     * 
     * @param uid
     * @param mobile
     * @param next_pid
     * @param token
     * @return
     * @time 2018年7月29日 下午3:59:31
     * @author chendong
     */
//    public String GetVcodeAndHoldMobilenum(String uid, String mobile, String next_pid, String token) {
////        if (uid == null || token == null) {
////            return "参数传入有误";
////        }
////        else {
////            String result = "";
////            return result;
////        }
//        return null;
//    }

    /**
     * 获取验证码并释放
//     * @param uid 用户ID （同获取号码）
//     * @param mobile 手机号码
//     * @param pid 项目ID
//     * @param token 令牌
//     * @param author_uid 开发者用户名
//     * @return
     * @time 2018年7月29日 下午6:01:39
     * @author chendong
     */
    public String getVcodeAndReleaseMobile(String mobile) {
        if (uid == null || pid == null || token == null) {
            return "用户名或密码不能为空";
        }
        else {
            //            uid={0}&mobile={1}&pid={2}&token={3}&author_uid={4}
            String paramContent = "uid=" + uid + "&pid=" + pid + "&token=" + token + "&mobile=" + mobile
                    + "&author_uid=" + author_uid;
            System.out.println(GETMOBILENUMURL);
            System.out.println(paramContent);
            String result = submitPost(GETVCODEANDRELEASEMOBILEURL, paramContent, "utf-8").toString();// .Post(API.LoginIn, String.Format("uid={0}&pwd={1}", uid, pwd));
            return result;
        }
    }

    /// <summary>
    /// 添加黑名单
    /// </summary>
    /// <param name="uid">用户ID （同获取号码）</param>
    /// <param name="mobiles">以,号分隔的手机号列表</param>
    /// <param name="pid">项目ID</param>
    /// <param name="token">令牌</param>
    /// <returns></returns>
    public String AddIgnoreList(String mobiles) {
        if (uid == null || pid == null || token == null) {
            return "参数传入有误";
        }
        else {
            String paramContent = "uid=" + uid + "&pid=" + pid + "&token=" + token + "&mobile=" + mobiles;
            System.out.println(ADDIGNORELISTURL);
            System.out.println(paramContent);
            String result = submitPost(ADDIGNORELISTURL, paramContent, "utf-8").toString();// .Post(API.LoginIn, String.Format("uid={0}&pwd={1}", uid, pwd));
            return result;
        }
    }

    /**
     * 获取当前用户正在使用的号码列表
     * 
//     * @param uid 用户ID （同获取号码）
//     * @param pid 项目ID
//     * @param token 令牌
     * @return
     * @time 2018年7月29日 下午6:01:02
     * @author chendong
     */
    public String GetRecvingInfo() {
        if (uid == null || pid == null || token == null) {
            return "参数传入有误";
        }
        else {
            String paramContent = "uid=" + uid + "&pid=" + pid + "&token=" + token;
            System.out.println(GETRECVINGINFO);
            System.out.println(paramContent);
            String result = submitPost(GETRECVINGINFO, paramContent, "utf-8").toString();// .Post(API.LoginIn, String.Format("uid={0}&pwd={1}", uid, pwd));
            return result;
        }
    }

    /// <summary>
    /// 取消所有短信接收，可立即解锁所有被锁定的金额
    /// </summary>
    /// <param name="uid">用户ID （同获取号码）</param>
    /// <param name="token">令牌</param>
    /// <returns></returns>

//    public String CancelSMSRecvAll(String uid, String token) {
//        if (uid == null || token == null) {
//            return "参数传入有误";
//        }
//        else {
//            String result = "";
//            return result;
//        }
//    }

    /// <summary>
    /// 取消一个短信接收，可立即解锁被锁定的金额
    /// </summary>
    /// <param name="uid"></param>
    /// <param name="mobile"></param>
    /// <param name="token"></param>
    /// <returns></returns>
//    public String CancelSMSRecv(String uid, String mobile, String token) {
//        if (uid == null || token == null) {
//            return "参数传入有误";
//        }
//        else {
//            String result = "";
//            return result;
//        }
//    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthor_uid() {
        return author_uid;
    }

    public void setAuthor_uid(String author_uid) {
        this.author_uid = author_uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAuthor_pwd() {
        return author_pwd;
    }

    public void setAuthor_pwd(String author_pwd) {
        this.author_pwd = author_pwd;
    }

    public static StringBuffer submitPost(String url, String paramContent, String codetype) {
        StringBuffer responseMessage = null;
        java.net.URLConnection connection = null;
        java.net.URL reqUrl = null;
        OutputStreamWriter reqOut = null;
        InputStream in = null;
        BufferedReader br = null;
        String param = paramContent;
        try {
            responseMessage = new StringBuffer();
            reqUrl = new java.net.URL(url);
            connection = reqUrl.openConnection();
            connection.setDoOutput(true);
            reqOut = new OutputStreamWriter(connection.getOutputStream());
            reqOut.write(param);
            reqOut.flush();
            int charCount = -1;
            in = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(in, codetype));
            while ((charCount = br.read()) != -1) {
                responseMessage.append((char) charCount);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("url=" + url + "?" + paramContent + "\n e=" + ex);
        }
        finally {
            try {
                in.close();
                reqOut.close();
            }
            catch (Exception e) {
                System.out.println("paramContent=" + paramContent + "|err=" + e);
            }
        }
        return responseMessage;
    }
}
