package backend.controller;

import backend.model.po.Dataset;
import backend.model.po.Experiment;
import backend.service.DataService;
import backend.service.ScenarioService;
import backend.util.json.HttpResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/scenario")
public class ScenarioController {

    @Autowired
    ScenarioService scenarioService ;
    @Autowired
    DataService dataService;


    @PostMapping(value = "/callAlgorithm")
    public Map callAlgorithm(
            @RequestParam("algorithmName")String algorithmName,
            @RequestBody Map<String,Object> input
    ){
        return scenarioService.callAlgorithm(algorithmName,input);
    }

    //为组件设置参数
    //TODO 为组件存储参数和运行结果数据
    @PostMapping(value = "/setParams")
    public void setParams(@RequestBody Map<String,Object> params) {

    }



    //创建实验
    @GetMapping(value = "/createExperiment")
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

    @GetMapping(value = "/getDataset")
    public Map<String,Object> getDataset(
            @RequestParam("userID") Long userID
    ) {
        List<Dataset> list = scenarioService.findDatasetByUserID(userID);
        Map<String,Object> result = HttpResponseHelper.newResultMap();
        result.put("list",list);
        return result;
    }






}
