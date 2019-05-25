package backend.feign.feignservice;

import java.io.File;
import java.io.IOException;
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
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UploadFileExec {


    private String serverURL;

    public UploadFileExec(@Value("${ml.feign.url}") String serverURL) {

        initUrl(serverURL);

    }

    private void initUrl(String url) {
        this.serverURL = url;
    }


    public String dummy(File file, String target) throws IOException {

        String setIdURL = serverURL + "/dummy/";

        String result = "";

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpPost httppost = new HttpPost(setIdURL);

            // 默认的的ContentType是application/octet-stream
            FileBody bin = new FileBody(file); // ContentType.MULTIPART_FORM_DATA

            StringBody targetBody = new StringBody(target, ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("csv_file", bin).addPart("target", targetBody).build();

            httppost.setEntity(reqEntity);

            System.out.println("----------------------------------------");
            System.out.print("Executing request " + httppost.getRequestLine() + ", ");
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {

                System.out.println(response.getStatusLine());

                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == HttpStatus.SC_OK) {

                    result = EntityUtils.toString(resEntity, "utf-8");

                    System.out.println(resEntity.getContent());

                } else if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {

                    result = statusCode + ": dummy 发生异常，请检查服务端异常问题";
                }

                return result;

            } finally {

                response.close();
            }
        } finally {
            httpclient.close();
        }

    }


    public String setID(File file) throws Exception {

        String setIdURL = serverURL + "/setId/";

        String result = "";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(setIdURL);

            // 默认的的ContentType是application/octet-stream
            FileBody bin = new FileBody(file); // ContentType.MULTIPART_FORM_DATA
            // FileBody bin = new FileBody(new File(path), ContentType.create("image/png"));

            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("csv_file", bin).build();

            httppost.setEntity(reqEntity);

            System.out.println("----------------------------------------");
            System.out.print("Executing request " + httppost.getRequestLine() + ", ");
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {

                System.out.println(response.getStatusLine());

                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == HttpStatus.SC_OK) {

                    result = EntityUtils.toString(resEntity, "utf-8");

//                    System.out.println("服务器正常返回的数据: " + result);// httpclient自带的工具类读取返回数据

                    System.out.println(resEntity.getContent());

                } else if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {

                    result = statusCode + ": setID 发生异常，请检查服务端异常问题";
                }

                return result;

            } finally {

                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

}
