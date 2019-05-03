package backend.feign.feignservice;

import backend.feign.feignclient.PicClassificationFeign;
import backend.service.ScenarioService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class PicClassificationExec {

    @Autowired
    PicClassificationFeign picClassificationFeign;

    @Autowired
    ScenarioService scenarioService;

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
     * @param dirParams
     * @return {'status': 'success',
     * 'reason': '成功创建训练集文件夹'}
     */
    public Map<String, ?> createTrainDir(
            String userName,
            Map<String, String> dirParams
    ) {

        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("exp_name", dirParams.get("expName"));
        map.put("dir_name", dirParams.get("dirName"));
        Map<String, String> createTrainDir = picClassificationFeign.create_train_dir(map);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", createTrainDir.get("status"));
        resultMap.put("reason", createTrainDir.get("reason"));
        return resultMap;

    }

    /**
     * 开始训练
     *
     * @param username
     * @param trainParams
     * @return
     */

    public Map<String, Object> picTrain(
            //:实验者的用户名
            String username,
            Map<String, Object> trainParams
//            String exp_name,//:实验名称
//            String batch,//:每次读入的块大小（越大对GPU要求越高，也可能更准确）
//            String epoach,//:批次（训练多少次)
//            String optimizer,//：优化器（默认ADAM,也可以使用SGD等）可选参数
//            String input_model//：自定义神经网络模型
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("exp_name", trainParams.get("expName"));
        map.put("batch", trainParams.get("batch"));
        map.put("epoach", trainParams.get("epoach"));
        if (trainParams.get("optimizer") != null) {
            map.put("optimizer", trainParams.get("optimizer"));
        }
        if (trainParams.get("inputModel") != null) {
            map.put("input_model", trainParams.get("inputModel"));
        }
        return picClassificationFeign.pic_train(map);
    }


    /**
     * 获取结果
     *
     * @param userId
     * @param taskID
     * @return
     */
    public Map<String, Object> getTrainResult(
            String userId,
            String taskID,
            String experimentID,
            String nodeNo
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", taskID);

        Map<String, Object> trainResMap = picClassificationFeign.train_result(map);

        Map<String, Object> resMap = new HashMap<>();
        //训练完成
        if (!trainResMap.get("result").equals("实验仍在训练中")) {

//            resMap.put("result", "实验训练结束！");
            //存贮到数据库
            JSONObject jasonObject = JSONObject.parseObject(String.valueOf(trainResMap.get("result")));
            Map<String, Object> annMapRes = (Map<String, Object>) jasonObject;
//            Map<String, Object> annMapRes = (Map<String, Object>) trainResMap.get("result");
            Map<String, Object> annParams = scenarioService.setParams(
                    new ArrayList<>(Arrays.asList(
                            "History Loss",
                            "History Acc",
                            "Test Loss",
                            "Test Acc"
                    )));

            Map<String, Object> annData = scenarioService.setAnnData(annMapRes);
            scenarioService.saveComputingResult(Long.parseLong(userId), Long.parseLong(experimentID), nodeNo, "table", annParams, annData);


            return scenarioService.getDataSet(Long.parseLong(userId), Long.parseLong(experimentID), nodeNo);
        }
        //训练未完成
        else {
            resMap.clear();
            resMap.put("result", "实验仍在训练中。");
            return resMap;
        }

    }

    public Map<String, Object> uploadPics(
            String userName,
            String expName,
            String dirName,
            List<MultipartFile> pics
    ) {

        Map<String, Object> map = new HashMap<>();

        map.put("username", userName);
        map.put("exp_name", expName);
        map.put("dir_name", dirName);
        map.put("files", pics);

        Map<String, Object> resMap = picClassificationFeign.upload_pics(map);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", resMap.get("status"));
        resultMap.put("reason", resMap.get("reason"));
        return resultMap;
    }

}
