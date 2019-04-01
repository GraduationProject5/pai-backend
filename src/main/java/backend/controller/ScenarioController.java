package backend.controller;

import backend.model.po.DataSet;
import backend.model.po.EdgePO;
import backend.model.po.Experiment;
import backend.model.po.NodePO;
import backend.model.vo.EdgeVO;
import backend.model.vo.ExperimentVO;
import backend.model.vo.NodeVO;
import backend.service.DataService;
import backend.service.ScenarioService;
import backend.service.UserService;
import backend.util.json.HttpResponseHelper;
import jdk.nashorn.internal.objects.annotations.Getter;
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

    /**
     * 更新实验信息
     * @param userID
     * @param experimentID
     * @param params
     * @return
     */
    @PostMapping(value = "/updateExperimentInfo")
    public Map<String,Object> updateExperimentInfo(
            @SessionAttribute("userID")String userID,
            @RequestParam("experimentID")Long experimentID,
            @RequestBody Map<String,Object> params
    ){
        ExperimentVO experimentVO = new ExperimentVO();
        experimentVO.experimentID =  experimentID ;
        experimentVO.experimentName=(String)params.get("experimentName");
        experimentVO.description=(String)params.get("description");
        return dataService.updateExperimentInfo(experimentVO);
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
            @SessionAttribute("userID") String userID,
            @RequestParam("experimentID") Long experimentID,
            @RequestParam("nodeID") Long nodeID
    ) {
        return scenarioService.findDataset(Long.parseLong(userID),experimentID,nodeID);
    }


    /** 保存实验的场景
     *
     * @param params
     *      [SessionAttribute] userID  ,
     *      实验id，
     *      List<nodeVO>，
     *      List<edgeVO>
     * @return  {"result":success} / {"result":false,"message":"..."}
     */
    @PostMapping(value = "/saveScenario")
    public Map<String,Object> saveScenario(
            @SessionAttribute("userID") String userID,
            @RequestBody Map<String,Object> params
    ){
        Map<String,Object> result = HttpResponseHelper.newResultMap();
//        String token = (String) params.get("token");
//        Long userID = userService.getUserIDByToken(token);

        if(Long.parseLong(userID)>0 ) {  //认证user
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
            @SessionAttribute("userID")String userID,
            @RequestParam("experimentID")Long experimentID
    ) {
//        Long userID = userService.getUserIDByToken(token);
        if(Long.parseLong(userID)>0) {  //认证user
            return scenarioService.getScenario(experimentID);
        } else {
            Map<String,Object> result = HttpResponseHelper.newResultMap();
            result.put("result", false);
            result.put("message", "not authorized");
            return result;
        }

    }

    //保存组件参数到NodePO
    @PostMapping(value = "/saveSettingsForNode")
    public boolean saveParamsForNode(
            @RequestBody Map<String,Object> params) {
        Long nodeID = (Long) params.get("nodeID");
        Map<String,Object> settings = (Map<String, Object>)params.get("settings");
        return scenarioService.saveSettingsForNode(nodeID,settings);
    }



    //获取Section和Component关系
    @GetMapping(value = "/getSectionsAndComponents")
    public List<Map<String,Object>> getSectionsAndComponents(){
        return scenarioService.getSectionsAndComponents();
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
