package backend.service;

//import model.*;

import backend.model.po.EdgePO;
import backend.model.po.NodePO;

import java.util.List;
import java.util.Map;

public interface ScenarioService {

    /** 根据算法名字（算法组件的简写）和对应的输入参数调用算法
     *
     * @param algorithmName
     * @param input
     * @return
     */
    Map callAlgorithm(String algorithmName, Map<String,Object> input);


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

    /** 检查算法的输入参数的类型是否正确
     *
     * @param algorithmName
     * @return
     */
    boolean checkInputForAlgorithm(String algorithmName,Map<String,Object> input);

    /** 检查调用算法的返回数据的类型是否正确
     *
     * @param algorithmName
     * @param input
     * @return
     */
    boolean checkOutputForAlgorithm(String algorithmName,Map<String,Object> input);

    /** 为输入数据进行封装
     *
     * @param node
     * @return
     */
    Map<String,Object> formatInputForAlgorithm(NodePO node) ;

}
