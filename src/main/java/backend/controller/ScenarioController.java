package backend.controller;

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
import backend.util.json.HttpResponseHelper;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/scenario")
public class ScenarioController {

    @Autowired
    UserService userService;
    @Autowired
    ScenarioService scenarioService;
    @Autowired
    DataService dataService;

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
        String nodeNo =  (String) params.get("nodeNo");
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
            long experimentID = experimentInfoVO.getExperimentID();
            List<NodeVO> nodeVOList = experimentInfoVO.getNodes();
            List<EdgeVO> edgeVOList = experimentInfoVO.getEdges();
            List<NodePO> nodePOList = new ArrayList<>();
            List<EdgePO> edgePOList = new ArrayList<>();
            for (NodeVO nodeVO :
                    nodeVOList) {
//                int componentID ;
                //TODO componentID
                NodePO nodePO = new NodePO(nodeVO, 2, experimentID);
                System.out.println(nodePO.getIndex() + "  happy");
                nodePOList.add(nodePO);
            }

            for (EdgeVO edgeVO :
                    edgeVOList) {
                EdgePO edgePO = new EdgePO(edgeVO, experimentID);
                System.out.println(edgePO.getIndex() + "  happy");
                edgePOList.add(edgePO);
            }

            //拓扑出执行序列，执行序列输出的是 节点的id
            IndegreeTable indegreeTable = new IndegreeTable(nodePOList, edgePOList);
            List<String> executeLine = indegreeTable.getResultOutputList();
            System.out.println(executeLine + " happy");

            //根据节点的id，查找节点的算法名称label
            //funLine -> 节点算法名称链表
            List<String> funLine = new ArrayList<>();
            for (String nodeID :
                    executeLine) {
                String funName = scenarioService.getFunNameByNodeID(nodeID);
                funLine.add(funName);
            }

            httpResult.put("result", funLine);

            scenarioService.executeLine(funLine);

            return httpResult;
        } else {
            httpResult.put("Error", "数据导出失败！");
            return httpResult;
        }
    }
}