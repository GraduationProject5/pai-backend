package backend.feign.feignservice;

import java.io.File;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UploadFileExec {


    //    @Value("${ml.feign.url}")
    static String serverURL = "http://127.0.0.1:8000";

    public Object setID(File file) throws Exception {

        String setIdURL = serverURL + "/setId/";

        System.out.println(setIdURL);

        String result = "";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(setIdURL);

            // 默认的的ContentType是application/octet-stream
            FileBody bin = new FileBody(file); // ContentType.MULTIPART_FORM_DATA
            // FileBody bin = new FileBody(new File(path), ContentType.create("image/png"));

            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("csv_file", bin).build();

            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");

                System.out.println(response.getStatusLine());

                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }

                int statusCode = response.getStatusLine().getStatusCode();

                System.out.println("服务器相应状态码----------------------------------------" + statusCode);

                if (statusCode == HttpStatus.SC_OK) {

                    result = EntityUtils.toString(resEntity, "utf-8");

                    System.out.println("服务器正常返回的数据: " + result);// httpclient自带的工具类读取返回数据

                    System.out.println(resEntity.getContent());

                } else if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {

                    System.out.println("上传文件发生异常，请检查服务端异常问题");
                }


//                reqEntity.writeTo();

//                StringEntity stringEntity=new StringEntity(EntityUtils.toString(resEntity),"utf-8");
//                return EntityUtils.toString(resEntity);
//                EntityUtils.consume(resEntity);
                return result;

//                return EntityUtils.toString(stringEntity);

            } finally {

                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

}
