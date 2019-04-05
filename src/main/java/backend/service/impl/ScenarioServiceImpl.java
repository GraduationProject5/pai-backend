package backend.service.impl;

import backend.daorepository.*;
import backend.feign.feignservice.EvaluationService;
import backend.feign.feignservice.MLService;
import backend.feign.feignservice.TextAnalysisService;
import backend.model.po.*;
import backend.model.vo.EdgeVO;
import backend.model.vo.NodeVO;
import backend.service.*;
import backend.util.json.HttpResponseHelper;
import backend.util.json.JSONHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
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
    ExperimentRepository experimentRepository;
    @Autowired
    RUserExperimentRepository rUserExperimentRepository;
    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    ComponentRepository componentRepository;
    @Autowired
    RSectionComponentRepository rSectionComponentRepository;
    @Autowired
    EdgePORepository edgePORepository;
    @Autowired
    NodePORepository nodePORepository;
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
    public boolean saveScenario(Long experimentID,
                                List<NodeVO> nodeVOList,
                                List<EdgeVO> edgeVOList) {
        nodeVOList = JSONHelper.toNodeVOList(nodeVOList);
        edgeVOList = JSONHelper.toEdgeVOList(edgeVOList);

        //转换没问题，清空场景
        clearScenario(experimentID);

        for(NodeVO nodeVO:nodeVOList) {
            NodePO nodePO = new NodePO
                    (nodeVO,getComponentIDByFuncName(nodeVO.label),experimentID);
//            nodePO =
            nodePORepository.save(nodePO); //返回带有id的nodePO
            //在第一次运行时才初始化对应的DataSet
//            Long nodeID = nodePO.getNodeID();
        }
        for(EdgeVO edgeVO:edgeVOList){
            EdgePO edgePO = new EdgePO
                    (edgeVO,experimentID);
            edgePORepository.save(edgePO);
        }

        return true;
    }

    @Override
    public Map<String,Object> getScenario(Long experimentID) {
        Map<String,Object> result = HttpResponseHelper.newResultMap();
        Experiment experiment = experimentRepository.findByExperimentID(experimentID);
        List<NodePO> nodePOList = nodePORepository.findByExperimentID(experimentID);
        List<EdgePO> edgePOList = edgePORepository.findByExperimentID(experimentID);
        List<DataSet> dataSetList = dataSetRepository.findByExperimentID(experimentID);
        List<DataParam> dataParamList = dataParamRepository.findByExperimentID(experimentID);
        List<DataResult> dataResultList = dataResultRepository.findByExperimentID(experimentID);

        result.put("experimentID",experimentID);
        result.put("experimentName",experiment.getExperimentName());
        result.put("description",experiment.getDescription());

        List<NodeVO> nodeVOList = new ArrayList<>();
        List<EdgeVO> edgeVOList = new ArrayList<>();
        for(NodePO nodePO:nodePOList){
            nodeVOList.add(nodePO.toNodeVO());
        }
        for(EdgePO edgePO:edgePOList){
            edgeVOList.add(edgePO.toEdgeVO());
        }

        result.put("nodes",nodeVOList);
        result.put("edges",edgeVOList);

        result.put("dataset",dataSetList);
        result.put("dataparams",dataParamList);
        result.put("dataresults",dataResultList);
        return result;
    }

    @Override
    public void clearScenario(Long experimentID) {
        dataResultRepository.deleteByExperimentID(experimentID);
        dataParamRepository.deleteByExperimentID(experimentID);
        dataSetRepository.deleteByExperimentID(experimentID);

        List<NodePO> nodePOList = nodePORepository.findByExperimentID(experimentID);
        List<EdgePO> edgePOList = edgePORepository.findByExperimentID(experimentID);
        for(NodePO nodePO:nodePOList){
            nodePORepository.delete(nodePO);
        }
        for (EdgePO edgePO:edgePOList){
            edgePORepository.delete(edgePO);
        }
//        edgePORepository.deleteByExperimentID(experimentID);
//        nodePORepository.deleteByExperimentID(experimentID);
    }

    @Override
    public boolean saveSettingsForNode(Long nodeID, Map<String, Object> settings) {
        NodePO nodePO = nodePORepository.findByNodeID(nodeID);
        nodePO.setSettings(settings);
        nodePORepository.save(nodePO);
        return true;
    }

    @Override
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    @Override
    public List<Component> getAllComponents() {
        return componentRepository.findAll();
    }

    @Override
    public List<R_Section_Component> getRelationForSectionsAndComponents() {
        return rSectionComponentRepository.findAll();
    }

    @Override
    public List<Map<String,Object>> getSectionsAndComponents() {
        List<R_Section_Component> r_section_componentList = getRelationForSectionsAndComponents();
        List<Map<String,Object>> sectionList = new ArrayList<>();   //返回结果

        Map<Integer,List<Component>> sectionMap = new HashMap<>();  //用来存储已经添加过的SectionID和已添加的Component

        for(R_Section_Component r_section_component:r_section_componentList) {
            int sectionID = r_section_component.getSectionID();
            int componentID = r_section_component.getComponentID();

            Section section;
            Component component = componentRepository.findByComponentID(componentID);

            if(sectionMap.containsKey(sectionID)){
                //找到节点
                List<Component> componentList = sectionMap.get(sectionID);
                componentList.add(component);
                sectionMap.put(sectionID,componentList);
            }
            else {
                section = sectionRepository.findBySectionID(sectionID);
                //创建新的节点
                Map<String,Object> subNode = HttpResponseHelper.newResultMap();
                subNode.put("sectionID",sectionID);
                subNode.put("sectionName",section.getSectionName());
                sectionList.add(subNode);

                List<Component> componentList = new ArrayList<>();
                componentList.add(component);
                sectionMap.put(sectionID,componentList);
            }
        }
        for (Map<String,Object> map:sectionList){
            int sectionID = (int)map.get("sectionID");
            List<Component> list = sectionMap.get(sectionID);
            map.put("components",list);
        }

       return sectionList;
    }

    @Override
    public void saveComputingResult(Long userID, Long experimentID, Long nodeID, String type,
                                    Map<String,Object> params,Map<String,Object> data) {
        clearNodeDataByNodeID(nodeID);

        DataSet dataSet = new DataSet();
        dataSet.setUserID(userID);
        dataSet.setExperimentID(experimentID);
        dataSet.setNodeID(nodeID);
        dataSet.setType(type);
        dataSet = dataSetRepository.save(dataSet);  //get ID
        Long dataSetID = dataSet.getDataSetID();
        DataParam dataParam = new DataParam();
        dataParam.setDataSetID(dataSetID);
        dataParam.setExperimentID(experimentID);
        dataParam.setParam(params);
        dataParam = dataParamRepository.save(dataParam);

        DataResult dataResult = new DataResult();
        dataResult.setDataSetID(dataSetID);
        dataResult.setExperimentID(experimentID);
        dataResult.setData(data);
        dataSet = dataSetRepository.save(dataSet);
    }



    @Override
    public void clearNodeDataByNodeID(Long nodeID) {
        List<DataSet> dataSetList = dataSetRepository.findByNodeID(nodeID);
        for(DataSet dataSet : dataSetList){
            Long dataSetID = dataSet.getDataSetID();
            dataParamRepository.deleteAllByDataSetID(dataSetID);
            dataResultRepository.deleteAllByDataSetID(dataSetID);
        }
        dataSetRepository.deleteAllByNodeID(nodeID);
    }

    //todo
    @Override
    public Map<String, Object> formatInputForAlgorithm(NodePO node) {
        return null;
    }

    @Override
    public Map<String, Object> findDataset(Long userID,Long experimentID,Long nodeID) {
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
        results.put("data",dataResultsMapList);

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
    public Map<String,Object> callAlgorithm(String algorithmName,Map<String,Object> input) {

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

    public int getComponentIDByFuncName(String funcName){
        Component component = componentRepository.findByFuncName(funcName);
        if(component==null)
            return -1;
        return component.getComponentID();
    }

    public int getComponentIDByComponentName(String componentName){
        Component component = componentRepository.findByComponentName(componentName);
        if(component==null)
            return -1;
        return component.getComponentID();
    }

    public void executeExperiment() {

    }

    public String getFunNameByNodeID(String nodeID) {

        NodePO nodePO = nodePORepository.findByNodeNo(nodeID);

        return nodePO.getLabel();
    }

}
