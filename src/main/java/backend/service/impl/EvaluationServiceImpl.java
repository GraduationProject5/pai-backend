package backend.service.impl;

import backend.feign.EvaluationFeign;
import backend.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Service
@RequestMapping(value = "/evaluation")
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    EvaluationFeign evaluationFeign;

    public Map cluster_evaluation(Map<String, Object> map) {
        return evaluationFeign.cluster_evaluation(map);
    }

    public Map regression_evaluation( Map<String, Object> map){
        return evaluationFeign.regression_evaluation(map);
    }

    public Map tcd( Map<String, Object> map){
        return evaluationFeign.tcd(map);
    }

    @GetMapping(value = "/mcd")
    public Map mcd( Map<String, Object> map){
        return evaluationFeign.mcd(map);
    }

    @GetMapping(value = "/confusion_matrix")
    public Map confusion_matrix( Map<String, Object> map){
        return evaluationFeign.confusion_matrix(map);
    }


}
