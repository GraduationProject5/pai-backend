package backend.util.register.phone;


import org.springframework.util.DigestUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by lienming on 2019/1/21.
 */
public class HttpUtil {

    /**
     * 构造通用参数timestamp、sig和respDataType
     *
     * @return
     */
    public static String createCommonParam()
    {
        // 时间戳 ，接口格式不能改变
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());

        // 签名MD5(ACCOUNT SID + AUTH TOKEN + timestamp)。共32位（小写）。内容不包含”+”号。
        String sigStr = Config.ACCOUNT_SID + Config.AUTH_TOKEN + timestamp ;
        InputStream is = new ByteArrayInputStream(sigStr.getBytes());
        String sig = "";
        try {
            sig = DigestUtils.md5DigestAsHex(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "&timestamp=" + timestamp  + "&sig=" + sig +"&respDataType=" + Config.RESP_DATA_TYPE;
    }



    public static String post(String url, String body){
        String result = "";
        try
        {
            OutputStreamWriter out = null;
            BufferedReader in = null;

            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();

            // 设置连接参数
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(20000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 提交数据
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(body);
            out.flush();

            // 读取返回数据
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = "";
            boolean firstLine = true; // 读第一行不加换行符
            while ((line = in.readLine()) != null)
            {
                if (firstLine)
                {
                    firstLine = false;
                } else
                {
                    result += System.lineSeparator();
                }
                result += line;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }


}
