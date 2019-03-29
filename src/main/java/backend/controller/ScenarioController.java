package backend.controller;

import backend.model.po.DataSet;
import backend.model.po.EdgePO;
import backend.model.po.Experiment;
import backend.model.po.NodePO;
import backend.model.vo.EdgeVO;
import backend.model.vo.NodeVO;
import backend.service.DataService;
import backend.service.ScenarioService;
import backend.service.UserService;
import backend.util.json.HttpResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/scenario")
public class ScenarioController {

    @Autowired
    UserService userService;
    @Autowired
    ScenarioService scenarioService ;
    @Autowired
    DataService dataService;
    //创建实验
    @PostMapping(value = "/createExperiment")
    public Map<String,Object> createExperiment(
            @SessionAttribute("userID")String userID ,
            @RequestParam("experimentName") String experimentName,
            @RequestParam("description") String description) {
        Map<String,Object> result = HttpResponseHelper.newResultMap();

        long experimentID = dataService.
                createExperiment(Long.parseLong(userID),experimentName,description);
        if(experimentID>0) {
            result.put("result",true);
            result.put("experimentID", experimentID);
        } else {
            result.put("result", false);
        }

        return result;
    }

    //查看实验
    @GetMapping(value = "/allExperiment")
    public Map<String,Object> allExperiment(
            @SessionAttribute("userID")String userID) {
        Map<String,Object> result = HttpResponseHelper.newResultMap();

        List<Experiment> list = dataService.getExperimentsByUser(Long.parseLong(userID));

        result.put("experiments",list );
        return result;
    }

    @GetMapping(value = "/getDataSet")
    public Map<String,Object> getDataSet(
            @RequestParam("userID") Long userID,
            @RequestParam("experimentID") Long experimentID,
            @RequestParam("nodeID") Long nodeID
    ) {
        return scenarioService.findDatasetByUserIDAndExperimentIDAndNodeID(userID,experimentID,nodeID);
    }


    /** 保存实验的场景
     *
     * @param params
     *      用户token，
     *      实验id，
     *      List<nodeVO>，
     *      List<edgeVO>
     * @return  {"result":success} / {"result":false,"message":"..."}
     */
    @PostMapping(value = "/saveScenario")
    public Map<String,Object> saveScenario(
            @RequestBody Map<String,Object> params
    ){
        Map<String,Object> result = HttpResponseHelper.newResultMap();
        String token = (String) params.get("token");
        Long userID = userService.getUserIDByToken(token);
        if(userID>0) {  //认证user
            int experimentID = (int) params.get("experimentID");
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
        } else {
            result.put("result", false);
            result.put("message", "not authorized");
            return result;
        }
    }

    @GetMapping(value = "/getScenario")
    public Map<String,Object> getScenario(
            @RequestParam("token")String token,
            @RequestParam("experimentID")Long experimentID
    ) {
        Long userID = userService.getUserIDByToken(token);
        if(userID>0) {  //认证user
            return scenarioService.getScenario(experimentID);
        } else {
            Map<String,Object> result = HttpResponseHelper.newResultMap();
            result.put("result", false);
            result.put("message", "not authorized");
            return result;
        }

    }

    //保存组件参数到NodePO
    //TODO 为组件存储参数和运行结果数据
    @PostMapping(value = "/saveParamsForNode")
    public void saveParamsForNode(
            @RequestBody Map<String,Object> params) {


    }



    //调用算法
    @PostMapping(value = "/callAlgorithm")
    public Map<String,Object> callAlgorithm(
            @RequestParam("algorithmName")String algorithmName,
            @RequestBody Map<String,Object> input
    ){
        return scenarioService.callAlgorithm(algorithmName,input);
    }




}
