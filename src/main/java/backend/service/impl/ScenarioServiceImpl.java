package backend.service.impl;

import backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ScenarioServiceImpl implements ScenarioService {

    @Autowired
    EvaluationService evaluationService;
    @Autowired
    MLService mlService;
    @Autowired
    TextAnalysisService textAnalysisService;
    @Autowired
    DataService dataService;

    /** 根据算法名字（算法组件的简写）和对应的输入参数调用算法
     *
     * @param algorithmName
     * @param input
     * @return
     */
    public Map callAlgorithm(String algorithmName,Map<String,Object> input) {

        Map result = null ;

        switch (algorithmName) {

            //case in EvaluationService
            case "ce":{
                result = evaluationService.cluster_evaluation(input);
                break;
            }
            case "re":{
                result = evaluationService.regression_evaluation(input);
                break;
            }
            case "tcd":{
                result = evaluationService.tcd(input);
                break;
            }
            case "mcd":{
                result = evaluationService.mcd(input);
                break;
            }
            case "cm":{
                result = evaluationService.confusion_matrix(input);
                break;
            }

            //case in MLServiceImpl
            case "svm":{
                result = mlService.support_vector_machine(input);
                break;
            }
            case "lr":{
                result = mlService.logic_regression(input);
                break;
            }
            case "GBDT":{
                result = mlService.gbdt_binary_classification(input);
                break;
            }
            case "knn":{
                result = mlService.k_nearest_neighbors(input);
                break;
            }
            case "rf":{
                result = mlService.random_forest(input);
                break;
            }
            case "nb":{
                result = mlService.naive_bayes(input);
                break;
            }
            case "...":{
                result = mlService.linear_regression(input);
                break;
            }
            case "GBDT_regression":{
                result = mlService.gbdt_regression(input);
                break;
            }
            case "KMeans":{
                result = mlService.k_means_cluster(input);
                break;
            }

            //case in TextAnalysisService
            case "par":{

            }

        }

        return result;
    }



//    //Section>Component
//    public Section createSection(){
//        return null;
//    }
//
//    public Component createComponent(){
//        return null;
//    }


}
