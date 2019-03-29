package backend.service;

//import model.*;

import backend.model.po.Component;
import backend.model.po.EdgePO;
import backend.model.po.NodePO;
import backend.model.po.Section;
import backend.model.vo.EdgeVO;
import backend.model.vo.NodeVO;

import java.util.List;
import java.util.Map;

public interface ScenarioService {

    /** 根据算法名字（算法组件的简写）和对应的输入参数调用算法
     *
     * @param algorithmName
     * @param input
     * @return
     */
    Map<String,Object> callAlgorithm(String algorithmName, Map<String,Object> input);


    /** 查找实验下连接组件的边
     * @param experimentID
     * @return
     */
    List<EdgePO> findEdgesByExperimentID(Long experimentID) ;


    /** 查找实验下所有使用的组件
     *
     * @param experimentID
     * @return
     */
    List<NodePO> findNodesByExperimentID(Long experimentID) ;

    /** 通过组件节点的NodeID查找组件的算法名称缩写,如"ce"
     *
     * @param nodeID
     * @return
     */
    String findAlgorithmNameByNodeID(Long nodeID) ;

    /** 保存场景
     *
     */
    boolean saveScenario(Long experimentID, List<NodeVO> nodeVOList, List<EdgeVO> edgeVOList);

    /**
     * 获取场景
     */
    Map<String,Object> getScenario( Long experimentID);

    /**
     * 清空场景
     * @param experimentID
     */
    void clearScenario(Long experimentID);

    /**
     * 保存组件参数
     */
    boolean saveSettingsForNode(Long nodeID,Map<String,Object> settings);

    /**
     * 获取所有Section
     */
    List<Section> getAllSections();

    /**
     * 获取所有Component
     */
    List<Component> getAllComponents();

    /**
     * 提供给算法调用后，保存运算中间结果的数据 DataSet
     */
    void saveComputingResult(Long userID,Long experimentID,Long nodeID,String type,
                             Map<String,Object> params,Map<String,Object> data);

    /** 为输入数据进行封装
     *
     * @param node
     * @return
     */
    Map<String,Object> formatInputForAlgorithm(NodePO node) ;

    Map<String,Object> findDatasetByUserIDAndExperimentIDAndNodeID(Long userID,Long experimentID,Long nodeID);

}
