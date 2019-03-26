package backend.service.impl;

import backend.feign.EvaluationFeign;
import backend.feign.MLFeign;
import backend.feign.TextAnalysisFeign;
import backend.service.ScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ScenarioServiceImpl implements ScenarioService {

    @Autowired
    EvaluationFeign evaluationFeign;
    @Autowired
    MLFeign mlFeign;
    @Autowired
    TextAnalysisFeign textAnalysisFeign;

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
                result = evaluationFeign.cluster_evaluation(input);
                break;
            }
            case "re":{
                result = evaluationFeign.regression_evaluation(input);
                break;
            }
            case "tcd":{
                result = evaluationFeign.tcd(input);
                break;
            }
            case "mcd":{
                result = evaluationFeign.mcd(input);
                break;
            }
            case "cm":{
                result = evaluationFeign.confusion_matrix(input);
                break;
            }

            //case in MLService
            case "svm":{
                result = mlFeign.support_vector_machine(input);
                break;
            }
            case "lr":{
                result = mlFeign.logic_regression(input);
                break;
            }
            case "GBDT":{
                result = mlFeign.gbdt_binary_classification(input);
                break;
            }
            case "knn":{
                result = mlFeign.k_nearest_neighbors(input);
                break;
            }
            case "rf":{
                result = mlFeign.random_forest(input);
                break;
            }
            case "nb":{
                result = mlFeign.naive_bayes(input);
                break;
            }
            case "...":{
                result = mlFeign.linear_regression(input);
                break;
            }
            case "....":{
                result = mlFeign.gbdt_regression(input);
                break;
            }
            case ".....":{
                result = mlFeign.k_means_cluster(input);
                break;
            }

            //case in TextAnalysisService
            case "par":{

            }

        }

        return null;
    }


//    public Model createModel() {
//        return null;
//    }
//
//    //Section>Component
//    public Section createSection(){
//        return null;
//    }
//
//    public Component createComponent(){
//        return null;
//    }


}
