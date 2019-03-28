package backend.service.impl;

import backend.daorepository.*;
import backend.feign.feignservice.EvaluationService;
import backend.feign.feignservice.MLService;
import backend.feign.feignservice.TextAnalysisService;
import backend.model.po.*;
import backend.service.*;
import backend.util.json.HttpResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    DataSetRepository dataSetRepository;
    @Autowired
    DataParamRepository dataParamRepository;
    @Autowired
    DataResultRepository dataResultRepository ;

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
    public Map<String, Object> formatInputForAlgorithm(NodePO node) {
        return null;
    }

    @Override
    public Map<String, Object> findDatasetByUserIDAndExperimentIDAndNodeID(Long userID,Long experimentID,Long nodeID) {
        DataSet dataSet =  dataSetRepository.findByUserIDAndExperimentIDAndNodeID(userID,experimentID,nodeID);
        long dataSetID = dataSet.getDataSetID();
        List<DataParam> dataParamList = dataParamRepository.findByDataSetID(dataSetID);
        List<DataResult> dataResultList = dataResultRepository.findByDataSetID(dataSetID);
        Map<String, Object> result = HttpResponseHelper.newResultMap();

        result.put("id",dataSetID) ; //实验结果ID
        result.put("experimentId",experimentID) ;


        Map<String, Object> results = HttpResponseHelper.newResultMap();
        results.put("id",nodeID);
        results.put("type",dataSet.getType());
        List<Map<String,Object>> dataParamsMapList = new ArrayList<>();
        for(DataParam dp:dataParamList){
            dataParamsMapList.add(dp.getParam());
        }
        results.put("paras",dataParamsMapList);

        Map<String, Object> data = HttpResponseHelper.newResultMap();
        List<Map<String,Object>> dataResultsMapList = new ArrayList<>();
        for(DataResult dr:dataResultList){
            dataResultsMapList.add(dr.getData());
        }
        results.put("data",dataParamsMapList);

        result.put("results",results);
        return result;
//        result.put("dataSet",dataSet);
//        result.put("dataParams",dataParamList);
//        result.put("dataResults",dataResultList);

    }


    /** 根据算法名字（算法组件的简写）和对应的输入参数调用算法
     *
     * @param algorithmName
     * @param input
     * @return
     */
    @Override
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
