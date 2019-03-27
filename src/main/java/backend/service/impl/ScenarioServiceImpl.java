package backend.service.impl;

import backend.daorepository.ComponentRepository;
import backend.daorepository.EdgePORepository;
import backend.daorepository.NodePORepository;
import backend.feign.feignservice.EvaluationService;
import backend.feign.feignservice.MLService;
import backend.feign.feignservice.TextAnalysisService;
import backend.model.po.Component;
import backend.model.po.EdgePO;
import backend.model.po.NodePO;
import backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    EdgePORepository edgePORepository;
    @Autowired
    NodePORepository nodePORepository;
    @Autowired
    ComponentRepository componentRepository;

    @Override
    public List<EdgePO> findEdgesByExperimentID(Long experimentID) {
        return edgePORepository.findByExperimentID(experimentID);
    }

    @Override
    public List<NodePO> findNodesByExperimentID(Long experimentID) {
        return nodePORepository.findByExperimentID(experimentID);
    }

    @Override
    public String findAlgorithmNameByNodeID(Long nodeID) {
        NodePO nodePO = nodePORepository.findByNodeID(nodeID) ;
        int componentID = nodePO.getComponentID();
        Component component = componentRepository.findByComponentID(componentID);
        String algorithmName = component.getFuncName();
        return algorithmName;
    }

    @Override
    public boolean checkInputForAlgorithm(String algorithmName,Map<String,Object> input) {
        switch (algorithmName) {
            //case in EvaluationService
            case "ce":{
                Object labels_true = input.get("labels_true");
                Object labels_pred = input.get("labels_pred");
                if(labels_true instanceof int[]
                        && labels_pred instanceof int[] )
                    return true;
                else
                    return false;
            }
            case "re":{
                Object y_true = input.get("y_true");
                Object y_pred = input.get("y_pred");
                if(y_true instanceof int[]
                        && y_pred instanceof int[] )
                    return true;
                else
                    return false;
            }
            case "tcd":{
                Object y_true = input.get("y_true");
                Object y_pred = input.get("y_pred");
                if(y_true instanceof int[]
                        && y_pred instanceof int[] )
                    return true;
                else
                    return false;
            }
            case "mcd":{
                Object y_true = input.get("y_true");
                Object y_pred = input.get("y_pred");
                if(y_true instanceof int[] && y_pred instanceof int[] )
                    return true;
                else
                    return false;
            }
            case "cm":{
                Object y_true = input.get("y_true");
                Object y_pred = input.get("y_pred");
                if(y_true instanceof int[] && y_pred instanceof int[] )
                    return true;
                else
                    return false;
            }

            //case in MLServiceImpl
            case "svm":{
                Object tol = input.get("tol");
                Object c = input.get("c");
                Object practice_x = input.get("practice_x");
                Object practice_y = input.get("practice_y");
                Object test_x = input.get("test_x");
                Object test_y = input.get("text_y");

                if(tol instanceof Double
                    && c instanceof Integer
                    && practice_x instanceof int[][]
                    && practice_y instanceof int[]
                    && test_x instanceof double[]
                    && test_y instanceof int[])
                    return true;
                else
                    return false;
            }
            case "lr":{
                Object tol = input.get("tol");
                Object c = input.get("c");
                Object penalty = input.get("penalty");
                Object X_train = input.get("X_train");
                Object y_train = input.get("y_train");
                Object X_test = input.get("X_test");
                if(tol instanceof Double
                        && c instanceof Integer
                        && penalty instanceof String
                        && X_train instanceof int[][]
                        && y_train instanceof int[]
                        && X_test instanceof double[][])
                    return true;
                else
                    return false;

            }
            case "GBDT":{
                Object loss = input.get("loss");
                Object learning_rate = input.get("learning_rate");
                Object n_estimators = input.get("n_estimators");
                Object subsample = input.get("subsample");
                Object min_samples_split = input.get("min_samples_split");
                Object min_samples_leaf = input.get("min_samples_leaf");
                Object max_depth = input.get("max_depth");
                Object alpha = input.get("alpha");
                Object verbose = input.get("verbose");
                Object X_train = input.get("X_train");
                Object y_train = input.get("y_train");
                Object X_test = input.get("X_test");

                if(loss instanceof String
                        && learning_rate instanceof Double
                        && n_estimators instanceof Integer
                        && subsample instanceof Integer
                        && min_samples_split instanceof Integer
                        && min_samples_leaf instanceof Integer
                        && max_depth instanceof Integer
                        && alpha instanceof Double
                        && verbose instanceof Integer
                        && X_train instanceof int[][]
                        && y_train instanceof int[]
                        && X_test instanceof double[][])
                    return true;
                else
                    return false;
            }
            case "knn":{
                Object k = input.get("k");
                Object X_train = input.get("X_train");
                Object y_train = input.get("y_train");
                Object X_test = input.get("X_test");
                if(k instanceof Integer
                        && X_train instanceof int[][]
                        && y_train instanceof int[]
                        && X_test instanceof double[][])
                    return true;
                else
                    return false;
            }
            case "rf":{
                //int n_estimators, int[][] X_train, int[] y_train, double[][] X_test
                Object n_estimators = input.get("n_estimators");
                Object X_train = input.get("X_train");
                Object y_train = input.get("y_train");
                Object X_test = input.get("X_test");
                if(n_estimators instanceof Integer
                        && X_train instanceof int[][]
                        && y_train instanceof int[]
                        && X_test instanceof double[][])
                    return true;
                else
                    return false;
            }
            case "nb":{

                Object X_train = input.get("X_train");
                Object y_train = input.get("y_train");
                Object X_test = input.get("X_test");
                if(X_train instanceof int[][]
                        && y_train instanceof int[]
                        && X_test instanceof double[][])
                    return true;
                else
                    return false;
            }
            case "linear":{

                Object X_train = input.get("X_train");
                Object y_train = input.get("y_train");
                Object X_test = input.get("X_test");
                if(X_train instanceof int[][]
                        && y_train instanceof int[]
                        && X_test instanceof double[][])
                    return true;
                else
                    return false;
            }
            case "GBDT_regression":{
                Object loss = input.get("loss");
                Object learning_rate = input.get("learning_rate");
                Object n_estimators = input.get("n_estimators");
                Object subsample = input.get("subsample");
                Object min_samples_split = input.get("min_samples_split");
                Object min_samples_leaf = input.get("min_samples_leaf");
                Object max_depth = input.get("max_depth");
                Object alpha = input.get("alpha");
                Object verbose = input.get("verbose");
                Object X_train = input.get("X_train");
                Object y_train = input.get("y_train");
                Object X_test = input.get("X_test");

                if(loss instanceof String
                        && learning_rate instanceof Double
                        && n_estimators instanceof Integer
                        && subsample instanceof Integer
                        && min_samples_split instanceof Integer
                        && min_samples_leaf instanceof Integer
                        && max_depth instanceof Integer
                        && alpha instanceof Double
                        && verbose instanceof Integer
                        && X_train instanceof int[][]
                        && y_train instanceof int[]
                        && X_test instanceof double[][])
                    return true;
                else
                    return false;
            }
            case "KMeans":{
                Object k = input.get("k");
                Object X_train = input.get("X_train");
                if(k instanceof Integer
                        && X_train instanceof int[][] )
                    return true;
                else
                    return false;
            }

            //case in TextAnalysisService
            case "par":{

            }

            case "sw":{

            }

            case "kv":{

            }

            case "lda":{

            }

            default: {
                return false ;
            }

        }
    }

    @Override
    public boolean checkOutputForAlgorithm(String algorithmName, Map<String, Object> input) {
        switch (algorithmName) {
            //case in EvaluationService
            case "ce":{
                Object adjusted_Rand_index = input.get("adjusted_Rand_index");
                Object mutual_information_based_scores = input.get("mutual_information_based_scores");
                Object homogeneity_score = input.get("homogeneity_score");
                Object completeness_score = input.get("completeness_score");
                Object v_measure_score = input.get("v_measure_score");
                Object fowlkes_mallows_score = input.get("fowlkes_mallows_score");

                if(adjusted_Rand_index instanceof Double
                        && mutual_information_based_scores instanceof Double
                        && homogeneity_score instanceof Double
                        && completeness_score instanceof Double
                        && v_measure_score instanceof Double
                        && fowlkes_mallows_score instanceof Double
                )
                    return true;
                else
                    return false;
            }
            case "re":{
                Object explained_variance_score = input.get("explained_variance_score");
                Object mean_absolute_error = input.get("mean_absolute_error");
                Object median_absolute_error = input.get("median_absolute_error");
                Object r2_score = input.get("r2_score");
                if(explained_variance_score instanceof Double
                        && mean_absolute_error instanceof Double
                        && median_absolute_error instanceof Double
                        && r2_score instanceof Double
                )
                    return true;
                else
                    return false;
            }
            case "tcd":{
                Object accuracy_score = input.get("accuracy_score");
                Object classification_report = input.get("classification_report");
                if(accuracy_score instanceof Double
                        && classification_report instanceof String )
                    return true;
                else
                    return false;
            }
            case "mcd":{
                Object accuracy_score = input.get("accuracy_score");
                Object classification_report = input.get("classification_report");
                if(accuracy_score instanceof Double
                        && classification_report instanceof String )
                    return true;
                else
                    return false;
            }
            case "cm":{
                Object confusion_matrix = input.get("confusion_matrix");
                if(confusion_matrix instanceof int[][]   )
                    return true;
                else
                    return false;
            }

            //case in MLServiceImpl
            case "svm":{
                Object result_y = input.get("result_y");

                if(result_y instanceof int[] )
                    return true;
                else
                    return false;
            }
            case "lr":{
                Object prediction_result = input.get("prediction_result");
                Object prediction_detail = input.get("prediction_detail");
                if(prediction_result instanceof int[]
                        && prediction_detail instanceof double[]
                      )
                    return true;
                else
                    return false;

            }
            case "GBDT":{
                //@return "prediction_result" :double[]
                Object prediction_result = input.get("prediction_result");

                if(prediction_result instanceof double[])
                    return true;
                else
                    return false;
            }
            case "knn":{
                Object prediction_result = input.get("prediction_result");
                Object prediction_detail = input.get("prediction_detail");
                if(prediction_result instanceof int[]
                        && prediction_detail instanceof double[][]
                )
                    return true;
                else
                    return false;
            }
            case "rf":{
                Object prediction_result = input.get("prediction_result");
                Object prediction_detail = input.get("prediction_detail");
                if(prediction_result instanceof int[]
                        && prediction_detail instanceof int[][]
                )
                    return true;
                else
                    return false;
            }
            case "nb":{
                Object prediction_result = input.get("prediction_result");
                Object prediction_detail = input.get("prediction_detail");
                if(prediction_result instanceof int[]
                        && prediction_detail instanceof int[][]
                )
                    return true;
                else
                    return false;
            }
            case "linear":{
                Object prediction_result = input.get("prediction_result");
                if(prediction_result instanceof double[])
                    return true;
                else
                    return false;
            }
            case "GBDT_regression":{
                Object prediction_result = input.get("prediction_result");
                if(prediction_result instanceof double[])
                    return true;
                else
                    return false;
            }
            case "KMeans":{
                Object prediction_result = input.get("prediction_result");
                if(prediction_result instanceof int[])
                    return true;
                else
                    return false;
            }

            //case in TextAnalysisService
            case "par":{

            }

            case "sw":{

            }

            case "kv":{

            }

            case "lda":{

            }

            default: {
                return false ;
            }

        }
    }

    @Override
    public Map<String, Object> formatInputForAlgorithm(NodePO node) {
        return null;
    }


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
            case "linear":{
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

            case "sw":{

            }

            case "kv":{

            }

            case "lda":{

            }

            default: {
                return null ;
            }

        }

        return result;
    }






}
