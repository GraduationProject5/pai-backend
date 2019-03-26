package backend.controller;

import backend.service.ScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/scenario")
public class ScenarioController {

    @Autowired
    ScenarioService scenarioService ;

    @PostMapping(value = "/callAlgorithm")
    public Map callAlgorithm(
            @RequestParam("algorithmName")String algorithmName,
            @RequestBody Map<String,Object> input
    ){
        return scenarioService.callAlgorithm(algorithmName,input);
    }








}
