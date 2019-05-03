package backend.feign.feignclient;


import backend.util.config.FeignConfig;
import feign.Logger;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
@FeignClient(url = "${ml.feign.url}", name = "algorithm", configuration = FeignConfig.class)
public interface PicClassificationFeign {

    /**
     * 创建实验目录
     *
     * @param map
     * @return
     */
    @PostMapping(value = "/create_exp/",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    Map<String, ?> create_exp(
            Map<String, ?> map
    );

    /**
     * 创建实验下训练类图片目录
     *
     * @param map
     * @return
     */
    @PostMapping(value = "/create_train_dir/",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    Map<String, String> create_train_dir(
            Map<String, ?> map
    );

    /**
     * 上传图片
     *
     * @param map
     * @return
     */
    @PostMapping(value = "/upload_pics/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    Map<String, Object> upload_pics(
            Map<String, ?> map
    );

    /**
     * 进行训练
     *
     * @param map
     * @return
     */
    @PostMapping(value = "/pic_train/",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    Map<String, Object> pic_train(
            Map<String, ?> map
    );


    /**
     * 获取训练结果
     *
     * @param map
     * @return
     */
    @PostMapping(value = "/train_result/",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    Map<String, Object> train_result(
            Map<String, ?> map
    );

}
