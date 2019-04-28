package backend.feign.feignservice;

import backend.feign.feignclient.PicClassificationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PicClassificationExec {

    @Autowired
    PicClassificationFeign picClassificationFeign;

    /**
     * 创建实验
     *
     * @param userName
     * @param expName
     * @return {'status': 'success',
     * 'reason': '成功创建实验文件夹'}
     */
    public Map<String, ?> createExp(
            String userName,
            String expName
    ) {
        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("exp_name", expName);
        return picClassificationFeign.create_exp(map);
    }

    /**
     * 创建文件夹
     *
     * @param userName
     * @param expName
     * @param dirName
     * @return {'status': 'success',
     * 'reason': '成功创建训练集文件夹'}
     */
    public Map<String, ?> createTrainDir(
            String userName,
            String expName,
            String dirName
    ) {

        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("exp_name", expName);
        map.put("dir_name", dirName);
        return picClassificationFeign.create_train_dir(map);

    }


}
