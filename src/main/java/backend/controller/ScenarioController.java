package backend.controller;

import backend.feign.feignservice.*;
import backend.model.bo.IndegreeTable;
import backend.model.po.DataSet;
import backend.model.po.EdgePO;
import backend.model.po.Experiment;
import backend.model.po.NodePO;
import backend.model.vo.EdgeVO;
import backend.model.vo.ExperimentInfoVO;
import backend.model.vo.ExperimentVO;
import backend.model.vo.NodeVO;
import backend.service.DataService;
import backend.service.ScenarioService;
import backend.service.UserService;
import backend.util.config.StaticVariable;
import backend.util.json.HttpResponseHelper;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

@RestController
@RequestMapping(value = "/api/scenario")
public class ScenarioController {

    @Autowired
    UserService userService;
    @Autowired
    ScenarioService scenarioService;
    @Autowired
    DataService dataService;
    @Autowired
    PretreatmentExec pretreatmentExec;
    @Autowired
    UploadFileExec uploadFileExec;
    @Autowired
    TextAnalysisExec textAnalysisExec;
    @Autowired
    EvaluationExec evaluationExec;
    @Autowired
    PicClassificationExec picClassificationExec;


    //创建实验
    @PostMapping(value = "/createExperiment")
    public Map<String, Object> createExperiment(
            @SessionAttribute("userID") String userID,
            @RequestParam("experimentName") String experimentName,
            @RequestParam(value = "description", required = false) String description) {

        String userName = userService.getUserNameByUserID(Long.parseLong(userID));
        System.out.println("================");
        System.out.println(picClassificationExec.createExp(userName, experimentName));

        return dataService.createExperiment(Long.parseLong(userID), experimentName, description);
    }

    /**
     * 更新实验信息
     *
     * @param userID
     * @param experimentID
     * @param params
     * @return
     */
    @PostMapping(value = "/updateExperimentInfo")
    public Map<String, Object> updateExperimentInfo(
            @SessionAttribute("userID") String userID,
            @RequestParam("experimentID") Long experimentID,
            @RequestBody Map<String, Object> params
    ) {
        ExperimentVO experimentVO = new ExperimentVO();
        experimentVO.experimentID = experimentID;
        experimentVO.experimentName = (String) params.get("experimentName");
        experimentVO.description = (String) params.get("description");
        return dataService.updateExperimentInfo(experimentVO);
    }

    //查看实验
    @GetMapping(value = "/allExperiment")
    public Map<String, Object> allExperiment(
            @SessionAttribute("userID") String userID) {
        Map<String, Object> result = HttpResponseHelper.newResultMap();

        List<Experiment> list = dataService.getExperimentsByUser(Long.parseLong(userID));

        result.put("experiments", list);
        return result;
    }

    @GetMapping(value = "/getDataSet")
    public Map<String, Object> getDataSet(
            @SessionAttribute("userID") String userID,
            @RequestParam("experimentID") Long experimentID,
            @RequestParam("nodeNo") String nodeNo
    ) {
        return scenarioService.getDataSet(Long.parseLong(userID), experimentID, nodeNo);
    }


    /**
     * 保存实验的场景
     *
     * @param params [SessionAttribute] userID  ,
     *               实验id，
     *               List<nodeVO>，
     *               List<edgeVO>
     * @return {"result":success} / {"result":false,"message":"..."}
     */
    @PostMapping(value = "/saveScenario")
    public Map<String, Object> saveScenario(
            @SessionAttribute("userID") String userID,
            @RequestBody Map<String, Object> params
    ) {
        int experimentID = (int) params.get("experimentID");
        Map<String, Object> result = HttpResponseHelper.newResultMap();

        List<NodeVO> nodeVOList;
        List<EdgeVO> edgeVOList;
        try {
            nodeVOList = (List<NodeVO>) params.get("nodes");
            edgeVOList = (List<EdgeVO>) params.get("edges");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result", false);
            result.put("message", e.getMessage());
            return result;
        }

        boolean isSuccess = scenarioService.saveScenario(Integer.toUnsignedLong(experimentID), nodeVOList, edgeVOList);
        result.put("result", isSuccess);
        return result;

    }

    @GetMapping(value = "/getScenario")
    public Map<String, Object> getScenario(
            @SessionAttribute("userID") String userID,
            @RequestParam("experimentID") Long experimentID
    ) {
        return scenarioService.getScenario(experimentID);
    }

    //保存组件参数到NodePO
    @PostMapping(value = "/saveSettingsForNode")
    public boolean saveParamsForNode(
            @SessionAttribute("userID") String userID,
            @RequestBody Map<String, Object> params) {
        String nodeNo = (String) params.get("nodeNo");
        Map<String, Object> settings = (Map<String, Object>) params.get("settings");
        return scenarioService.saveSettingsForNode(nodeNo, settings);
    }


    //获取Section和Component关系
    @GetMapping(value = "/getSectionsAndComponents")
    public List<Map<String, Object>> getSectionsAndComponents() {
        return scenarioService.getSectionsAndComponents();
    }

    //调用算法
    @PostMapping(value = "/callAlgorithm")
    public Map<String, Object> callAlgorithm(
            @RequestParam("algorithmName") String algorithmName,
            @RequestBody Map<String, Object> input
    ) {
        return scenarioService.callAlgorithm(algorithmName, input);
    }

    //点击运行实验
    @PostMapping(value = "/executeExperiment")
    public Map<String, Object> executeExperiment(
            @SessionAttribute("userID") String userID,
            @RequestParam("tableName") String tableName,
            @RequestBody ExperimentInfoVO experimentInfoVO
    ) throws IOException {

        Map<String, Object> httpResult = HttpResponseHelper.newResultMap();

        //导出数据为csv，返回导出文件路径，交给预处理
        String dataSourcePath = dataService.exportCsv(userID, tableName);

        Map sourceData = new HashMap();
        sourceData.put("data", new File(dataSourcePath));

        if (dataSourcePath.equals("")) {
            //获取实验的边和节点的信息
            long experimentID = experimentInfoVO.getExperimentID(); //实验id
            List<NodeVO> nodeVOList = experimentInfoVO.getNodes();
            List<EdgeVO> edgeVOList = experimentInfoVO.getEdges();

            List<NodePO> nodePOList = scenarioService.getNodePOListByNodeVOList(nodeVOList, experimentID);
//            System.out.println(nodePOList.size());
            List<EdgePO> edgePOList = scenarioService.getEdgePOListByEdgeVOList(edgeVOList, experimentID);

            //拓扑出执行序列，执行序列输出的是 节点的id
            IndegreeTable indegreeTable = new IndegreeTable(nodePOList, edgePOList);
            List<String> executeLine = indegreeTable.getResultOutputList();

            //根据节点的id，查找节点的算法名称label
            //funLine -> 节点算法名称链表
            List<String> funLine = new ArrayList<>();
            for (String nodeID :
                    executeLine) {
                String funName = scenarioService.getFunNameByNodeID(nodeID);
                funLine.add(funName);
            }

            System.out.println("执行序列: " + funLine);
            httpResult.put("result", funLine);

            scenarioService.executeLine(funLine, sourceData);

            return httpResult;
        } else {
            httpResult.put("Error", "数据导出失败！");
            return httpResult;
        }
    }

    //运行 文本分类
    @PostMapping(value = "/executeTextAnalysis")
    public Map<String, Object> executeTextAnalysis(
            @SessionAttribute("userID") String userID,
            @RequestParam("tableName") String tableName,
            @RequestParam("target") String target,
            @RequestBody ExperimentInfoVO experimentInfoVO
    ) throws IOException {


        Map<String, Object> httpResult = HttpResponseHelper.newLinkedResultMap();
        boolean result = true;
        httpResult.put("result", result);

        File file = new File(dataService.exportCsv(userID, tableName));

        //=========添加id列=========//
//        String setIdRes;
//        try {
//            setIdRes = uploadFileExec.setID(file);
//        } catch (Exception e) {
//            e.printStackTrace();
//            httpResult.clear();
//            httpResult.put("Exception: ", "setID 异常");
//            return httpResult;
//        }

        long experimentID = experimentInfoVO.getExperimentID(); //实验id
        long userId = Long.parseLong(userID);
        List<NodeVO> nodeVOList = experimentInfoVO.getNodes();
        List<NodePO> nodePOList = scenarioService.getNodePOListByNodeVOList(nodeVOList, experimentID);

        //=========哑变量=========//
//        String nodeID = nodePOList;
        String dummyRes;
        List<String> dummyList;
        //=========labels_name==========//
        Map<String, Object> labelNameMap;
        try {

            dummyRes = uploadFileExec.dummy(file, target);

            String[] dummyStrings = dummyRes.split("\\n");
            dummyList = dataService.stringArrayToList(dummyStrings);

//            dummyList.remove(0);

            //设置params参数
            Map<String, Object> dummyParams = scenarioService.setDummyParams(dummyList);

            //设置保存的数据
            Map<String, Object> dummyData = scenarioService.setDummyData(dummyList);

            String nodeNo = scenarioService.getNodeNoFromPoList(nodePOList, "哑变量");

            //label 的名称
            labelNameMap = scenarioService.getLabelName(dummyRes);

            scenarioService.saveComputingResult(userId, experimentID, nodeNo, "table", dummyParams, dummyData);
            httpResult.put("哑变量", "运行成功并保存结果！");

            file.delete(); //删除本地临时文件

        } catch (Exception e) {
            e.printStackTrace();
            httpResult.remove("result");
            result = false;
            httpResult.put("result", result);
            httpResult.put("reason", "运行 dummy 异常，保存数据失败!");
            return httpResult;
        }


        //=========分词 + 停词过滤=========//

        List<List<String>> partArray;//分词结果
        List<List<String>> swArray;
        try {
            List<List<List<String>>> partAndSwRes = scenarioService.executePartAndSw(dummyRes);
            partArray = partAndSwRes.get(0);//分词结果
            swArray = partAndSwRes.get(1);//停词结果


            Map<String, Object> partParams = scenarioService.setParams(new ArrayList<>(Arrays.asList("Seg_list")));
            Map<String, Object> partData = scenarioService.setPartData(partArray);

            String nodeNo1 = scenarioService.getNodeNoFromPoList(nodePOList, "分词");
            scenarioService.saveComputingResult(userId, experimentID, nodeNo1, "table", partParams, partData);

            Map<String, Object> swParams = scenarioService.setParams(new ArrayList<>(Arrays.asList("Stopped_tokens")));
            Map<String, Object> swData = scenarioService.setSwData(swArray);

            String nodeNo2 = scenarioService.getNodeNoFromPoList(nodePOList, "停词过滤");
            scenarioService.saveComputingResult(userId, experimentID, nodeNo2, "table", swParams, swData);

            httpResult.put("分词/停词过滤", "运行成功并保存结果！");

        } catch (Exception e) {
            e.printStackTrace();
            httpResult.remove("result");
            result = false;
            httpResult.put("result", result);
            httpResult.put("reason", "运行 分词/停词过滤 异常，保存数据失败!");
            return httpResult;
        }


        // 新闻主题的数量
        int n_topics = labelNameMap.size();

        //=========labels_true=========//
        List<Integer> trueLabels = scenarioService.getTrueLabels(dummyRes);

        Map<String, Object> trueLabelsMap = new HashMap<>();
        trueLabelsMap.put("true_labels", trueLabels);

        //=========词频统计===========//
        try {
            TextAnalysisExec.FrequencyStatistics frequencyStatistics = textAnalysisExec.new FrequencyStatistics();
            Map<String, Object> kvMap = new HashMap<>();
            List<Map<String, Object>> kvList = new ArrayList<>();
            int index = 0;
            for (List swString :
                    swArray) {

                Map<String, Object> tmpMap = new HashMap<>();
                tmpMap.put("id", index);
                tmpMap.put("text", swString);
                kvList.add(tmpMap);
                index++;
            }
            kvMap.put("news_list", kvList);
            Map<String, Object> kvMapRes = frequencyStatistics.handleFeign(kvMap);

//            System.out.println(kvMapRes);
            //设置参数
            Map<String, Object> kvParams = scenarioService.setKvParams(kvMapRes);

            //设置数值
            Map<String, Object> kvData = scenarioService.setKvData(kvMapRes);

            String nodeNo = scenarioService.getNodeNoFromPoList(nodePOList, "词频统计");
            scenarioService.saveComputingResult(userId, experimentID, nodeNo, "table", kvParams, kvData);

            httpResult.put("词频统计", "运行成功并保存结果！");

        } catch (Exception e) {
            e.printStackTrace();
            httpResult.remove("result");
            result = false;
            httpResult.put("result", result);
            httpResult.put("reason", "运行 词频统计 异常，保存数据失败!");
            return httpResult;
        }


        //=========LDA==========//
        Map<String, Object> ldaMapRes = new HashMap<>();
        try {
            TextAnalysisExec.LDA lda = textAnalysisExec.new LDA();
            Map<String, Object> ldaMap = new HashMap<>();
            List<String> ldaList = new ArrayList<>();
            for (List<String> textList :
                    swArray) {
                String textStr = "";
                for (String text :
                        textList) {
                    textStr = textStr + " " + text;
                }
                ldaList.add(textStr.trim());
            }
            ldaMap.put("corpus", ldaList);
            ldaMap.put("n_topics", n_topics);
            ldaMapRes = lda.handleFeign(ldaMap);

            Map<String, Object> ldaParams = scenarioService.setParams(new ArrayList<>(Arrays.asList("Docres")));
            Map<String, Object> ldaData = scenarioService.setLdaData(ldaMapRes);
            String nodeNo = scenarioService.getNodeNoFromPoList(nodePOList, "LDA");

            scenarioService.saveComputingResult(userId, experimentID, nodeNo, "table", ldaParams, ldaData);

            httpResult.put("lda", "运行成功并保存结果！");

        } catch (Exception e) {
            e.printStackTrace();
            httpResult.remove("result");
            result = false;
            httpResult.put("result", result);
            httpResult.put("reason", "运行 lda 异常，保存数据失败!");
            return httpResult;
        }


        //===========labels_pred=============//

        List<Integer> preLabels = scenarioService.getPreLabels(ldaMapRes);


        //===========聚类评估=============//

        try {
            EvaluationExec.ClusterEvaluation clusterEvaluation = evaluationExec.new ClusterEvaluation();
            Map<String, Object> ceMap = new HashMap<>();
            ceMap.put("labels_true", trueLabels);
            ceMap.put("labels_pred", preLabels);
            Map<String, Object> ceMapRes = clusterEvaluation.handleFeign(ceMap);


            List<String> ceParamsList = new ArrayList<>(Arrays.asList(
                    "adjusted_Rand_index",
                    "mutual_information_based_scores",
                    "homogeneity_score",
                    "completeness_score",
                    "v_measure_score",
                    "fowlkes_mallows_score"
            ));
            Map<String, Object> ceParams = scenarioService.setParams(ceParamsList);

            Map<String, Object> ceData = scenarioService.setCeData(ceParamsList, ceMapRes);

            String nodeNo = scenarioService.getNodeNoFromPoList(nodePOList, "聚类评估");
            scenarioService.saveComputingResult(userId, experimentID, nodeNo, "table", ceParams, ceData);

            httpResult.put("聚类评估", "运行成功并保存结果！");

        } catch (Exception e) {

            e.printStackTrace();
            httpResult.remove("result");
            result = false;
            httpResult.put("result", result);
            httpResult.put("reason", "运行 聚类评估 异常，保存数据失败!");
            return httpResult;

        }

//        scenarioService.saveComputingResult(userId, experimentID, "labels_name", "node", null, labelNameMap);
//        scenarioService.saveComputingResult(userId, experimentID, "true_labels", "node", null, trueLabelsMap);

        return httpResult;
    }


    /**
     * 开始图片训练
     *
     * @param userID
     * @param trainParams
     * @return
     */
    @PostMapping(value = "/executePicTrain")
    public Map<String, Object> executePicClassification(
            @SessionAttribute("userID") String userID,
            @RequestBody Map<String, Object> trainParams
    ) {

        String userName = userService.getUserNameByUserID(Long.parseLong(userID));

        return picClassificationExec.picTrain(userName, trainParams);
    }

    /**
     * 获取训练结果
     *
     * @param userID
     * @param taskID
     * @return
     */
    @GetMapping(value = "getPicTrainResult")
    public Map<String, Object> getPicTrainResult(
            @SessionAttribute("userID") String userID,
            @RequestParam("taskID") String taskID,
            @RequestParam("experimentID") String experimentID,
            @RequestParam("nodeNo") String nodeNo
    ) {

        String userName = userService.getUserNameByUserID(Long.parseLong(userID));

        return picClassificationExec.getTrainResult(userID, taskID, experimentID, nodeNo);

    }

}