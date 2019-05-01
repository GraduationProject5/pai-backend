package backend.service.impl;

import backend.daorepository.*;
import backend.feign.feignservice.TextAnalysisExec;
import backend.feign.feignservice.service.EvaluationService;
import backend.feign.feignservice.service.MLService;
import backend.feign.feignservice.service.TextAnalysisService;
import backend.model.po.*;
import backend.model.vo.EdgeVO;
import backend.model.vo.NodeVO;
import backend.service.*;
import backend.util.json.HttpResponseHelper;
import backend.util.json.JSONHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    DataResultRepository dataResultRepository;
    @Autowired
    TextAnalysisExec textAnalysisExec;

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
        NodePO nodePO = nodePORepository.findByNodeID(nodeID);
        int componentID = nodePO.getComponentID();
        Component component = componentRepository.findByComponentID(componentID);
        String algorithmName = component.getFuncName();
        return algorithmName;
    }

    @Override
    public boolean saveScenario(Long experimentID,
                                List<NodeVO> nodeVOList,
                                List<EdgeVO> edgeVOList) {
        if (nodeVOList != null)
            nodeVOList = JSONHelper.toNodeVOList(nodeVOList);
        if (edgeVOList != null)
            edgeVOList = JSONHelper.toEdgeVOList(edgeVOList);

        //转换没问题，清空场景
        clearScenario(experimentID);

        if (nodeVOList != null) {
            for (NodeVO nodeVO : nodeVOList) {
                NodePO nodePO = new NodePO
                        (nodeVO, getComponentIDByComponentName(nodeVO.label), experimentID);
//            nodePO =
                nodePORepository.save(nodePO); //返回带有id的nodePO
                //在第一次运行时才初始化对应的DataSet
//            Long nodeID = nodePO.getNodeID();
            }
        }
        if (edgeVOList != null) {
            for (EdgeVO edgeVO : edgeVOList) {
                EdgePO edgePO = new EdgePO
                        (edgeVO, experimentID);
                edgePORepository.save(edgePO);
            }
        }
        return true;
    }

    @Override
    public Map<String, Object> getScenario(Long experimentID) {
        Map<String, Object> result = HttpResponseHelper.newResultMap();
        Experiment experiment = experimentRepository.findByExperimentID(experimentID);
        List<NodePO> nodePOList = nodePORepository.findByExperimentID(experimentID);
        List<EdgePO> edgePOList = edgePORepository.findByExperimentID(experimentID);
        List<DataSet> dataSetList = dataSetRepository.findByExperimentID(experimentID);
        List<DataParam> dataParamList = dataParamRepository.findByExperimentID(experimentID);
        List<DataResult> dataResultList = dataResultRepository.findByExperimentID(experimentID);

        result.put("experimentID", experimentID);
        result.put("experimentName", experiment.getExperimentName());
        result.put("description", experiment.getDescription());

        List<NodeVO> nodeVOList = new ArrayList<>();
        List<EdgeVO> edgeVOList = new ArrayList<>();
        for (NodePO nodePO : nodePOList) {
            nodeVOList.add(nodePO.toNodeVO());
        }
        for (EdgePO edgePO : edgePOList) {
            edgeVOList.add(edgePO.toEdgeVO());
        }

        result.put("nodes", nodeVOList);
        result.put("edges", edgeVOList);

        result.put("dataset", dataSetList);
        result.put("dataparams", dataParamList);
        result.put("dataresults", dataResultList);
        return result;
    }

    @Override
    public void clearScenario(Long experimentID) {
        dataResultRepository.deleteByExperimentID(experimentID);
        dataParamRepository.deleteByExperimentID(experimentID);
        dataSetRepository.deleteByExperimentID(experimentID);
        nodePORepository.deleteByExperimentID(experimentID);
        edgePORepository.deleteByExperimentID(experimentID);
    }

    @Override
    public boolean saveSettingsForNode(String nodeNo, Map<String, Object> settings) {
        NodePO nodePO = nodePORepository.findByNodeNo(nodeNo);
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
    public List<Map<String, Object>> getSectionsAndComponents() {
        List<R_Section_Component> r_section_componentList = getRelationForSectionsAndComponents();
        List<Map<String, Object>> sectionList = new ArrayList<>();   //返回结果

        Map<Integer, List<Component>> sectionMap = new HashMap<>();  //用来存储已经添加过的SectionID和已添加的Component

        for (R_Section_Component r_section_component : r_section_componentList) {
            int sectionID = r_section_component.getSectionID();
            int componentID = r_section_component.getComponentID();

            Section section;
            Component component = componentRepository.findByComponentID(componentID);

            if (sectionMap.containsKey(sectionID)) {
                //找到节点
                List<Component> componentList = sectionMap.get(sectionID);
                componentList.add(component);
                sectionMap.put(sectionID, componentList);
            } else {
                section = sectionRepository.findBySectionID(sectionID);
                //创建新的节点
                Map<String, Object> subNode = HttpResponseHelper.newResultMap();
                subNode.put("sectionID", sectionID);
                subNode.put("sectionName", section.getSectionName());
                sectionList.add(subNode);

                List<Component> componentList = new ArrayList<>();
                componentList.add(component);
                sectionMap.put(sectionID, componentList);
            }
        }
        for (Map<String, Object> map : sectionList) {
            int sectionID = (int) map.get("sectionID");
            List<Component> list = sectionMap.get(sectionID);
            map.put("components", list);
        }

        return sectionList;
    }

    @Override
    public void saveComputingResult(Long userID, Long experimentID, String nodeNo, String type,
                                    Map<String, Object> params, Map<String, Object> data) {
        clearNodeDataByNodeNo(nodeNo);

        DataSet dataSet = new DataSet();
        dataSet.setUserID(userID);
        dataSet.setExperimentID(experimentID);
        dataSet.setNodeNo(nodeNo);
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
        dataResult = dataResultRepository.save(dataResult);
    }

    @Override
    public void clearNodeDataByNodeNo(String nodeNo) {
        List<DataSet> dataSetList = dataSetRepository.findByNodeNo(nodeNo);
        for (DataSet dataSet : dataSetList) {
            Long dataSetID = dataSet.getDataSetID();
            dataParamRepository.deleteAllByDataSetID(dataSetID);
            dataResultRepository.deleteAllByDataSetID(dataSetID);
        }
        dataSetRepository.deleteAllByNodeNo(nodeNo);
    }

    //todo
    @Override
    public Map<String, Object> formatInputForAlgorithm(NodePO node) {
        return null;
    }

    @Override
    public Map<String, Object> getDataSet(Long userID, Long experimentID, String nodeNo) {
        DataSet dataSet = dataSetRepository.findByUserIDAndExperimentIDAndNodeNo(userID, experimentID, nodeNo);
        Map<String, Object> result = HttpResponseHelper.newResultMap();
        //检查是否存在
        if (dataSet == null) {
            result.put("result", false);
            result.put("message", "can not find DataSet");
            return result;
        }

        long dataSetID = dataSet.getDataSetID();
        List<DataParam> dataParamList = dataParamRepository.findByDataSetID(dataSetID);
        List<DataResult> dataResultList = dataResultRepository.findByDataSetID(dataSetID);


        result.put("id", dataSetID); //实验结果ID
        result.put("experimentId", experimentID);


        Map<String, Object> results = HttpResponseHelper.newResultMap();
        results.put("id", nodeNo);
        results.put("type", dataSet.getType());
//        List<Map<String, Object>> dataParamsMapList = new ArrayList<>();
//        for (DataParam dp : dataParamList) {
//            dataParamsMapList.add(dp.getParam());
//        }
        results.put("paras", ((Map) dataParamList.get(0).getParam()).get("paras"));

        Map<String, Object> data = HttpResponseHelper.newResultMap();
//        List<Map<String, Object>> dataResultsMapList = new ArrayList<>();
//        for (DataResult dr : dataResultList) {
//            dataResultsMapList.add(dr.getData());
//        }
        results.put("data", ((Map) dataResultList.get(0).getData()).get("data"));

        result.put("results", results);
        return result;
//        result.put("dataSet",dataSet);
//        result.put("dataParams",dataParamList);
//        result.put("dataResults",dataResultList);

    }


    /**
     * 根据算法名字（算法组件的简写）和对应的输入参数调用算法
     *
     * @param algorithmName
     * @param input
     * @return
     */
    @Override
    public Map<String, Object> callAlgorithm(String algorithmName, Map<String, Object> input) {

        Map result = null;

        switch (algorithmName) {

            //case in EvaluationService
            case "ce": {
                result = evaluationService.cluster_evaluation(input);
                break;
            }
            case "re": {
                result = evaluationService.regression_evaluation(input);
                break;
            }
            case "tcd": {
                result = evaluationService.tcd(input);
                break;
            }
            case "mcd": {
                result = evaluationService.mcd(input);
                break;
            }
            case "cm": {
                result = evaluationService.confusion_matrix(input);
                break;
            }

            //case in MLServiceImpl
            case "svm": {
                result = mlService.support_vector_machine(input);
                break;
            }
            case "lr": {
                result = mlService.logic_regression(input);
                break;
            }
            case "GBDT": {
                result = mlService.gbdt_binary_classification(input);
                break;
            }
            case "knn": {
                result = mlService.k_nearest_neighbors(input);
                break;
            }
            case "rf": {
                result = mlService.random_forest(input);
                break;
            }
            case "nb": {
                result = mlService.naive_bayes(input);
                break;
            }
            case "linear": {
                result = mlService.linear_regression(input);
                break;
            }
            case "GBDT_regression": {
                result = mlService.gbdt_regression(input);
                break;
            }
            case "KMeans": {
                result = mlService.k_means_cluster(input);
                break;
            }

            //case in TextAnalysisService
            case "par": {

            }

            case "sw": {

            }

            case "kv": {

            }

            case "lda": {

            }

            default: {
                return null;
            }

        }

        return result;
    }

    public int getComponentIDByFuncName(String funcName) {
        Component component = componentRepository.findByFuncName(funcName);
        if (component == null)
            return -1;
        return component.getComponentID();
    }

    public int getComponentIDByComponentName(String componentName) {
        Component component = componentRepository.findByComponentName(componentName);
        if (component == null)
            return -1;
        return component.getComponentID();
    }

    public void executeLine(List<String> funLine) {


    }

    public String getFunNameByNodeID(String nodeID) {

        NodePO nodePO = nodePORepository.findByNodeNo(nodeID);

        return nodePO.getLabel();
    }

//    public int getComponentIDByComponentName(String componentName){
//
//        int componentID = componentRepository.findComponentIDByComponentName(componentName);
//
//        return componentID;
//    }

    public List<NodePO> getNodePOListByNodeVOList(List<NodeVO> nodeVOList, long experimentID) {

        List<NodePO> nodePOList = new ArrayList<>();

        for (NodeVO nodeVO :
                nodeVOList) {

            //获取node的组件ID
            String componentName = nodeVO.name;
            int componentID = this.getComponentIDByComponentName(componentName);

            NodePO nodePO = new NodePO(nodeVO, componentID, experimentID);
            nodePOList.add(nodePO);
        }

        return nodePOList;
    }

    public List<EdgePO> getEdgePOListByEdgeVOList(List<EdgeVO> edgeVOList, long experimentID) {

        List<EdgePO> edgePOList = new ArrayList<>();
        for (EdgeVO edgeVO :
                edgeVOList) {
            EdgePO edgePO = new EdgePO(edgeVO, experimentID);
            edgePOList.add(edgePO);
        }

        return edgePOList;
    }

    public List<List<List<String>>> executePartAndSw(String dummyRes) {

        List<List<List<String>>> res = new ArrayList<>();

        TextAnalysisExec.Participles participles = textAnalysisExec.new Participles();
        TextAnalysisExec.StopwordsFilter stopwordsFilter = textAnalysisExec.new StopwordsFilter();
        String[] dummyResSplit = dummyRes.split("\\n");
        List<String> dummyResList = dataService.stringArrayToList(dummyResSplit);
        dummyResList.remove(0);//删除第一行
        List<List<String>> partArray = new ArrayList();//分词结果
        List<List<String>> swArray = new ArrayList<>();//停词结果
        for (String temp :
                dummyResList) {
            //执行分词
            Map<String, Object> partMap = new HashMap<>();
            partMap.put("texts", temp);
            Map partMapRes = participles.handleFeign(partMap);
            partArray.add((List<String>) partMapRes.get("seg_list"));

            //停词过滤
            Map swMapRes = stopwordsFilter.handleFeign(partMapRes);
            swArray.add((List<String>) swMapRes.get("stopped_tokens"));
        }

        res.add(partArray);
        res.add(swArray);

        return res;
    }

    public Map<String, Object> getLabelName(String dummyRes) {

        Map<String, Object> labelMap = new HashMap<>();

        String label = dummyRes.split("\\n")[0];
        String[] strings = label.split(",");
        List<String> stringList = dataService.stringArrayToList(strings);
        stringList.remove(0);

        int index = 1;
        for (String str :
                stringList) {
            labelMap.put(str, index);
            index++;
        }

        return labelMap;
    }

    public List<Integer> getTrueLabels(String dummyRes) {
        List<Integer> trueLabels = new ArrayList<>();

        String[] dummyResSplit = dummyRes.split("\n");
        List<String> dummyResList = dataService.stringArrayToList(dummyResSplit);
        System.out.println(dummyResList.get(0));
        System.out.println(dummyResList.get(1));
        System.out.println(dummyResList.get(2));
        System.out.println(dummyResList.get(3));
        System.out.println(dummyResList.get(4));
        dummyResList.remove(0);//删除第一行
        for (String string :
                dummyResList) {
            String[] strings = string.split(",");
            List<String> stringList = dataService.stringArrayToList(strings);
            stringList.remove(0);
            int location = findLabelPosition(stringList);
            trueLabels.add(location);
        }


        return trueLabels;
    }

    public int findLabelPosition(List<String> stringList) {

        int location = 1;

        System.out.println(stringList);

        for (String string :
                stringList) {
            if (Integer.parseInt(string) == 1) {
                return location;
            } else if (Integer.parseInt(string) == 0) {
                location++;
            } else {
                System.out.println("labels不是常数！");
            }
        }
        return location;
    }

    public List<Integer> getPreLabels(Map<String, Object> ldaMapRes) {
        List<Integer> preLabels = new ArrayList<>();

        List<List<Double>> doubleLists = (List<List<Double>>) ldaMapRes.get("docres");

        for (List<Double> doubleList :
                doubleLists) {
            int maxIndex = doubleList.indexOf(Collections.max(doubleList));

            //+1 从1开始计数
            preLabels.add(maxIndex + 1);
        }

        return preLabels;

    }

    public String getNodeNoFromPoList(List<NodePO> nodePOList, String nodeName) {
        String nodeNo = null;

        for (NodePO nodePO :
                nodePOList) {

            if (nodePO.getName().equals(nodeName)) {

                nodeNo = nodePO.getNodeNo();
            }
        }
        return nodeNo;
    }

    public Map<String, Object> setDummyParams(List<String> dummyList) {

        Map<String, Object> dummyParams = new HashMap<>();//所有的

        List<Map<String, Object>> dummyParamList = new ArrayList<>();//存一个的链表

        String[] params = dummyList.get(0).split(",");

        for (String tmp :
                params) {
            Map<String, Object> dummyParam = new HashMap<>();//一个
            dummyParam.put("title", tmp);
            dummyParam.put("dataIndex", tmp);
            dummyParam.put("key", tmp);

            dummyParamList.add(dummyParam);
        }

        dummyParams.put("paras", dummyParamList);

        return dummyParams;
    }

    public Map<String, Object> setDummyData(List<String> dummyList) {

        Map<String, Object> dummyData = new HashMap<>();

        List<Map<String, Object>> dummyDataList = new ArrayList<>();

//        Map<String, Object> dummySingleData = new HashMap<>();

        String[] columns = dummyList.get(0).split(",");

        //去除首行，剩下data
        dummyList.remove(0);

        //插入数据
        for (int i = 0; i < dummyList.size(); i++) {
            String dummyStr = dummyList.get(i);
            String[] data = dummyStr.split(",");

            Map<String, Object> dummySingleData = new HashMap<>();
            dummySingleData.put("id", i);

            for (int j = 0; j < data.length; j++) {
                dummySingleData.put(columns[j], data[j]);
            }

            dummyDataList.add(dummySingleData);
        }

        dummyData.put("data", dummyDataList);

        return dummyData;
    }

    public Map<String, Object> setParams(List<String> params) {

        Map<String, Object> partParams = new HashMap<>();

        List<Map> partParamsList = new ArrayList<>();

        for (String param :
                params) {
            Map<String, Object> partParam = new HashMap<>();
            partParam.put("title", param);
            partParam.put("dataIndex", param.toLowerCase());
            partParam.put("key", param.toLowerCase());

            partParamsList.add(partParam);
        }


        partParams.put("paras", partParamsList);

        return partParams;
    }

    public Map<String, Object> setPartData(List<List<String>> partArray) {

        Map<String, Object> partData = new HashMap<>();

        List<Map<String, Object>> partDataList = new ArrayList<>();

        for (int i = 0; i < partArray.size(); i++) {
            Map<String, Object> partSingleData = new HashMap<>();
            List<String> partStr = partArray.get(i);
            String tmp = listToString(partStr);
            partSingleData.put("id", i);
            partSingleData.put("seg_list", tmp);
            partDataList.add(partSingleData);
        }

        partData.put("data", partDataList);

        return partData;

    }

    public Map<String, Object> setSwData(List<List<String>> partArray) {

        Map<String, Object> partData = new HashMap<>();

        List<Map<String, Object>> partDataList = new ArrayList<>();

        for (int i = 0; i < partArray.size(); i++) {
            Map<String, Object> partSingleData = new HashMap<>();
            List<String> partStr = partArray.get(i);
            String tmp = listToString(partStr);
            partSingleData.put("id", i);
            partSingleData.put("stopped_tokens", tmp);
            partDataList.add(partSingleData);
        }

        partData.put("data", partDataList);

        return partData;

    }

    public static String listToString(List<String> list) {

        if (list == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        boolean first = true;

        //第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }

    public Map<String, Object> setKvParams(Map<String, Object> kvMapRes) {
        Map<String, Object> kvParams = new HashMap<>();

        //kvs
        Map<String, Object> kvs = (Map) kvMapRes.get("kvs");

        List<Map<String, Object>> kvParamList = new ArrayList<>();

        Map<String, Object> tmp = new HashMap<>();
        tmp.put("title", "Word_list");
        tmp.put("dataIndex", "word_list");
        tmp.put("key", "word_list");

        kvParamList.add(tmp);

        for (int i = 0; i < kvs.size(); i++) {
            Map<String, Object> indexMap = new HashMap<>();
            indexMap.put("title", String.valueOf(i));
            indexMap.put("dataIndex", String.valueOf(i));
            indexMap.put("key", String.valueOf(i));
            kvParamList.add(indexMap);
        }


        kvParams.put("paras", kvParamList);
//        Map<String, Object> kvSingleParams = new HashMap<>();

        return kvParams;

    }

    public Map<String, Object> setKvData(Map<String, Object> kvMapRes) {

        Map<String, Object> kvData = new HashMap<>();

        //kvs
        Map<String, Object> kvs = (Map) kvMapRes.get("kvs");

        //kvs长度
        int kvsLength = kvs.size();

        //word_list
        List<String> wordList = (List<String>) kvMapRes.get("word_list");

        //wordList 长度
        int wordListLength = wordList.size();

        List<Map<String, Object>> kvDataList = new ArrayList<>();

        for (int i = 0; i < wordListLength; i++) {
            Map<String, Object> kvSingleData = new HashMap<>();
            kvSingleData.put("id", i);
            kvSingleData.put("word_list", wordList.get(i));
            for (int j = 0; j < kvsLength; j++) {
                // 一个kv
                Map<String, Integer> singleKvs = (Map<String, Integer>) kvs.get(String.valueOf(j));
                if (singleKvs.get(String.valueOf(i)) != null) {
                    kvSingleData.put(String.valueOf(j), String.valueOf(singleKvs.get(String.valueOf(i))));
                } else {
                    kvSingleData.put(String.valueOf(j), "0");
                }
            }
            kvDataList.add(kvSingleData);
        }

        kvData.put("data", kvDataList);

        return kvData;
    }

    public Map<String, Object> setLdaData(Map<String, Object> ldaMapRes) {
        Map<String, Object> ldaData = new HashMap<>();

        List<List<Double>> ldaLists = (List<List<Double>>) ldaMapRes.get("docres");

        List<Map<String, Object>> ldaDataList = new ArrayList<>();

        for (int i = 0; i < ldaLists.size(); i++) {
            Map<String, Object> ldaSingleData = new HashMap<>();
            ldaSingleData.put("id", String.valueOf(i));
            ldaSingleData.put("docres", ldaLists.get(i));
            ldaDataList.add(ldaSingleData);
        }

        ldaData.put("data", ldaDataList);

        return ldaData;
    }

    public Map<String, Object> setCeData(List<String> ceParamsList, Map<String, Object> ceMapRes) {
        Map<String, Object> ceData = new HashMap<>();

        List<Map<String, Object>> ceDataList = new ArrayList<>();

        Map<String, Object> ceSingleData = new HashMap<>();

        //就一个
        ceSingleData.put("id", "0");
        for (String paraStr :
                ceParamsList) {
            ceSingleData.put(paraStr, ceMapRes.get(paraStr));
        }

        ceDataList.add(ceSingleData);

        ceData.put("data", ceDataList);

        return ceData;

    }
}
