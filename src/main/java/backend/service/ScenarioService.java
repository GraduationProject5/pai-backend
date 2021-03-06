package backend.service;

//import model.*;

import backend.model.po.*;
import backend.model.vo.EdgeVO;
import backend.model.vo.NodeVO;

import java.util.List;
import java.util.Map;

public interface ScenarioService {

    /////////////////////////////////// 算法相关 begin //////////////////////////////////////

    /**
     * 根据算法名字（算法组件的简写）和对应的输入参数调用算法
     *
     * @param algorithmName
     * @param input
     * @return
     */
    Map<String, Object> callAlgorithm(String algorithmName, Map<String, Object> input);

    /**
     * 提供给算法调用后，保存运算中间结果的数据 DataSet
     */
    void saveComputingResult(Long userID, Long experimentID, String nodeNo, String type,
                             Map<String, Object> params, Map<String, Object> data);

    /**
     * 为输入数据进行封装
     *
     * @param node
     * @return
     */
    Map<String, Object> formatInputForAlgorithm(NodePO node);

    /////////////////////////////////// 算法相关 end //////////////////////////////////////

    /////////////////////////////////// 场景相关 begin //////////////////////////////////////

    /**
     * 查找实验下连接组件的边
     *
     * @param experimentID
     * @return
     */
    List<EdgePO> findEdgesByExperimentID(Long experimentID);


    /**
     * 查找实验下所有使用的组件
     *
     * @param experimentID
     * @return
     */
    List<NodePO> findNodesByExperimentID(Long experimentID);

    /**
     * 通过组件节点的NodeID查找组件的算法名称缩写,如"ce"
     *
     * @param nodeID
     * @return
     */
    String findAlgorithmNameByNodeID(Long nodeID);

    /**
     * 保存场景
     */
    boolean saveScenario(Long experimentID, List<NodeVO> nodeVOList, List<EdgeVO> edgeVOList);

    /**
     * 获取场景
     */
    Map<String, Object> getScenario(Long experimentID);

    /**
     * 获取文本分析场景
     */
    void saveTextAnalysisScenario(Long newExperimentID,Long userID);

    /**
     * 从模板保存图片场景
     *
     * @param newExperimentID
     * @param userID
     */
    void savePicTrainScenario(Long newExperimentID, Long userID);
    /**
     * 清空场景
     *
     * @param experimentID
     */
    void clearScenario(Long experimentID);

    /**
     * 保存组件参数
     */
    boolean saveSettingsForNode(String nodeNo, Map<String, Object> settings);

    /**
     * 获取单一节点的数据集
     */
    Map<String, Object> getDataSet(Long userID, Long experimentID,String nodeNo);

    /**
     * 清除节点的中间运算结果
     *
     */
    void clearNodeDataByNodeNo(String nodeNo);

    /**
     * 运行实验
     */
    void executeLine(List<String> funLine, Map<String, Object> data);

    /**
     * 根据节点id查找节点算法名称
     *
     * @param nodeID
     * @return
     */
    String getFunNameByNodeID(String nodeID);

    /////////////////////////////////// 场景相关 end //////////////////////////////////////

    /////////////////////////////////// Section和Component相关 begin //////////////////////////////////////

    /**
     * 获取所有Section
     */
    List<Section> getAllSections();

    /**
     * 获取所有Component
     */
    List<Component> getAllComponents();

    /**
     * 获取Component和Section的父子关系
     */
    List<R_Section_Component> getRelationForSectionsAndComponents();

    /**
     * 返回前端要求的Section和Component的嵌套关系格式
     */
    //todo 各种组件的参数设置需要人为规定！
    List<Map<String, Object>> getSectionsAndComponents();

    /**
     * 根据组件名称获取组件的id
     *
     * @param componentName
     * @return
     */
    int getComponentIDByComponentName(String componentName);

    /**
     * 根据方法名获取组件id
     *
     * @param funcName
     * @return
     */
    int getComponentIDByFuncName(String funcName);

    /**
     * 根据nodeVo list 获取PO list
     *
     * @param nodeVOList
     * @param experimentID
     * @return
     */
    List<NodePO> getNodePOListByNodeVOList(List<NodeVO> nodeVOList, long experimentID);

    /**
     * @param edgeVOList
     * @param experimentID
     * @return
     */
    List<EdgePO> getEdgePOListByEdgeVOList(List<EdgeVO> edgeVOList, long experimentID);

/////////////////////////////////// Section和Component相关 end  //////////////////////////////////////


    /////////////////////////////////// 运行场景相关  //////////////////////////////////////

    /**
     * 设置哑变量的参数
     *
     * @param dummyList
     * @return
     */
    Map<String, Object> setDummyParams(List<String> dummyList);


    /**
     * 设置哑变量数据
     *
     * @param dummyList
     * @return
     */
    Map<String, Object> setDummyData(List<String> dummyList);

    /**
     * set params
     *
     * @param string
     * @return
     */
    Map<String, Object> setParams(List<String> string);

    /**
     * part data
     *
     * @param partArray
     * @return
     */
    Map<String, Object> setPartData(List<List<String>> partArray);

    /**
     * sw data
     *
     * @param partArray
     * @return
     */
    Map<String, Object> setSwData(List<List<String>> partArray);

    /**
     * kv params
     *
     * @param kvMapRes
     * @return
     */
    Map<String, Object> setKvParams(Map<String, Object> kvMapRes);

    /**
     * kv data
     *
     * @param kvMapRes
     * @return
     */
    Map<String, Object> setKvData(Map<String, Object> kvMapRes);

    /**
     * ce data
     *
     * @param ceMapRes
     * @return
     */
    Map<String, Object> setCeData(List<String> ceParamsList, Map<String, Object> ceMapRes);


    /**
     * lda data
     *
     * @param ldaMapRes
     * @return
     */
    Map<String, Object> setLdaData(Map<String, Object> ldaMapRes);

    /**
     * ann data
     *
     * @param annMapRes
     * @return
     */
    Map<String, Object> setAnnData(Map<String, Object> annMapRes);

    /**
     * 获取nodeNo
     *
     * @param nodePOList
     * @param nodeName
     * @return
     */

    String getNodeNoFromPoList(List<NodePO> nodePOList, String nodeName);

    /**
     * 执行分词和停词过滤
     *
     * @param dummyRes
     * @return
     */
    List<List<List<String>>> executePartAndSw(String dummyRes);

    /**
     * 获取新闻分类
     *
     * @param dummyRes
     * @return
     */
    Map<String, Object> getLabelName(String dummyRes);

    /**
     * 获取整个新闻列表的true_label
     *
     *
     * 获取文本的实际分类结果
     *
     * @param dummyRes
     * @return
     */
    List<Integer> getTrueLabels(String dummyRes);


    /**
     * 获取文本的预测分类结果
     *
     * @param map
     * @return
     */
    List<Integer> getPreLabels(Map<String, Object> map);

}
