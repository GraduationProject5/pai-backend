package backend.feign;

import backend.service.DataService;
import backend.feign.EvaluationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/evaluation")
public class EvaluationService {

    @Autowired
    EvaluationFeign evaluationFeign;
    @Autowired
    DataService dataService;

    @GetMapping(value = "/cluster_evaluation")
    public Map cluster(@RequestBody Map<String, Object> map) {
        return evaluationFeign.cluster_evaluation(map);
    }

    @GetMapping(value = "/regression_evaluation ")
    public Map regression_evaluation(@RequestBody Map<String, Object> map){
        return evaluationFeign.regression_evaluation(map);
    }

    @GetMapping(value = "/tcd")
    public Map tcd(@RequestBody Map<String, Object> map){
        return evaluationFeign.tcd(map);
    }

    @GetMapping(value = "/mcd")
    public Map mcd(@RequestBody Map<String, Object> map){
        return evaluationFeign.mcd(map);
    }

    @GetMapping(value = "/confusion_matrix")
    public Map confusion_matrix(@RequestBody Map<String, Object> map){
        return evaluationFeign.confusion_matrix(map);
    }


}
