package backend.util.register.phone;

import java.net.URLEncoder;

/**
 * Created by lienming on 2019/1/20.
 */

//TODO 手机注册
public class RegisterHelper {
    private static String operation = "/industrySMS/sendSMS";

    private static String accountSid = Config.ACCOUNT_SID;
    private static String to = "15105180709";

    private static String code = smsCode();
    //  登录验证码：{1}，如非本人操作，请忽略此短信。
    private static String smsContent = "【南京大学机器学习平台】尊敬的用户，您的验证码为"+code;

    /**
     * 验证码通知短信
     */
    public static void execute()
    {
        String tmpSmsContent = null;
        try{
            tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
        }catch(Exception e){

        }
        String url = Config.BASE_URL + operation;
        String body = "accountSid=" + accountSid + "&to=" + to + "&smsContent=" + tmpSmsContent
                + HttpUtil.createCommonParam();

        // 提交请求
        String result = HttpUtil.post(url, body);
        System.out.println("result:" + System.lineSeparator() + result);
    }

    //创建验证码
    public static String smsCode(){
        String random=(int)((Math.random()*9+1)*100000)+"";
        System.out.println("验证码："+random);
        return random;
    }

    public static  void main(String[] agrs){
        RegisterHelper.execute();
    }
}
