package backend.controller;

import backend.feign.feignservice.EvaluationExec;
import backend.feign.feignservice.PretreatmentExec;
import backend.feign.feignservice.TextAnalysisExec;
import backend.feign.feignservice.UploadFileExec;
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
@RequestMapping(value = "/scenario")
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

    //创建实验
    @PostMapping(value = "/createExperiment")
    public Map<String, Object> createExperiment(
            @SessionAttribute("userID") String userID,
            @RequestParam("experimentName") String experimentName,
            @RequestParam("description") String description) {
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
    ) {

        Map<String, Object> httpResult = HttpResponseHelper.newResultMap();

        //导出数据为csv，返回导出文件路径，交给预处理
//        String dataSourcePath = dataService.exportCsv(userID, tableName);
//        if (dataSourcePath != null) {
        if (true) {
            //获取实验的边和节点的信息
            long experimentID = experimentInfoVO.getExperimentID(); //实验id
            List<NodeVO> nodeVOList = experimentInfoVO.getNodes();
            List<EdgeVO> edgeVOList = experimentInfoVO.getEdges();

            List<NodePO> nodePOList = scenarioService.getNodePOListByNodeVOList(nodeVOList, experimentID);
            System.out.println(nodePOList.size());
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

            scenarioService.executeLine(funLine);

            return httpResult;
        } else {
            httpResult.put("Error", "数据导出失败！");
            return httpResult;
        }
    }

    //文本分析
    @PostMapping(value = "/executeTextAnalysis")
    public Map<String, Object> executeTextAnalysis(
            @SessionAttribute("userID") String userID,
            @RequestParam("tableName") String tableName,
            @RequestParam("target") String target,
            @RequestParam("n_topics") int n_topics
    ) throws IOException {
        Map<String, Object> httpResult = HttpResponseHelper.newLinkedResultMap();

        File file = dataService.exportCsv(userID, tableName);

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

        //=========哑变量=========//
        String dummyRes;
        try {
            dummyRes = uploadFileExec.dummy(file, target);

        } catch (Exception e) {
            e.printStackTrace();
            httpResult.clear();
            httpResult.put("Exception: ", "dummy 异常");
            return httpResult;
        }

        file.delete(); //删除本地临时文件

        //=========分词 + 停词过滤=========//
        List<List<List<String>>> partAndSwRes = scenarioService.executePartAndSw(dummyRes);
        List<List<String>> partArray = partAndSwRes.get(0);//分词结果
        List<List<String>> swArray = partAndSwRes.get(1);//停词结果

        //=========labels_name==========//
        Map<Integer, String> labelNameMap = scenarioService.getLabelName(dummyRes);

        //=========labels_true=========//
        List<Integer> trueLabels = scenarioService.getTrueLabels(dummyRes);

        //=========词频统计===========//
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
        Map kvMapRes = frequencyStatistics.handleFeign(kvMap);

        //=========LDA==========//
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
        Map<String, Object> ldaMapRes = lda.handleFeign(ldaMap);

        //===========labels_pred=============//

        List<Integer> preLabels = scenarioService.getPreLabels(ldaMapRes);


        //===========聚类评估=============//
        EvaluationExec.ClusterEvaluation clusterEvaluation = evaluationExec.new ClusterEvaluation();
        Map<String, Object> ceMap = new HashMap<>();
//        List<Integer> trueList = new ArrayList<>(Arrays.asList(2, 0, 2, 2, 0, 1));
//        List<Integer> predList = new ArrayList<>(Arrays.asList(0, 0, 2, 2, 0, 2));
        ceMap.put("labels_true", trueLabels);
        ceMap.put("labels_pred", preLabels);
        Map ceMapRes = clusterEvaluation.handleFeign(ceMap);

        httpResult.put("哑变量结果", dummyRes);
        httpResult.put("labelMap", labelNameMap);
        httpResult.put("true_labels", trueLabels);
        httpResult.put("pre_labels", preLabels);
        httpResult.put("分词结果", partArray);
        httpResult.put("停词过滤", swArray);
        httpResult.put("词频统计", kvMapRes);//仅进行展示数据
        httpResult.put("LDA结果", ldaMapRes);
        httpResult.put("聚类结果", ceMapRes);
        return httpResult;
    }

}