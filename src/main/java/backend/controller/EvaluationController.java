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
    public Map cluster(
            @RequestBody Map<String, Object> map
    ) {
        return evaluationService.cluster_evaluation(map);
    }

    @GetMapping(value = "/re")
    public void re(){

    }

    @GetMapping(value = "/tcd")
    public void tcd(){

    }

    @GetMapping(value = "/mcd")
    public void mcd(){

    }

    @GetMapping(value = "/confusion_matrix")
    public void confusionMatrix(){

    }


}
