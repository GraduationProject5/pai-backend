package backend.controller;

import backend.service.DataService;
import backend.algoservice.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/evaluation")
public class EvaluationController {

    @Autowired
    EvaluationService evaluationService;
    @Autowired
    DataService dataService;

    @GetMapping(value = "/cluster_evaluation")
    public Map cluster(@RequestBody Map<String, Object> map) {
        return evaluationService.cluster_evaluation(map);
    }

    @GetMapping(value = "/regression_evaluation ")
    public Map regression_evaluation(@RequestBody Map<String, Object> map){
        return evaluationService.regression_evaluation(map);
    }

    @GetMapping(value = "/tcd")
    public Map tcd(@RequestBody Map<String, Object> map){
        return evaluationService.tcd(map);
    }

    @GetMapping(value = "/mcd")
    public Map mcd(@RequestBody Map<String, Object> map){
        return evaluationService.mcd(map);
    }

    @GetMapping(value = "/confusion_matrix")
    public Map confusion_matrix(@RequestBody Map<String, Object> map){
        return evaluationService.confusion_matrix(map);
    }


}
