package backend.model.bo;

import backend.model.po.EdgePO;
import backend.model.po.NodePO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

/**
 * 使用EdgePO、NodePO构建一个有向无环图的节点的入度表
 */
@Getter
@Setter
public class IndegreeTable {

    Map<Long,Integer> inDegreeMap;

    List<NodePO> nodeList ;
    List<EdgePO> edgePOList;

    List<Long> resultOutputList;

    public IndegreeTable(List<NodePO> nodeList,List<EdgePO> edgePOList) {
        this.nodeList = nodeList;
        this.edgePOList = edgePOList;
        initMap();
        topologicalSort();
    }

    /**
     * 用这个方法返回执行的节点顺序,NodeID
     * @return
     */
    public List<Long> getExecuteOrder(){
        return this.resultOutputList;
    }

    public void getTopologyString(){
        for(Long l : this.resultOutputList) {
            System.out.print(l+",");
        }
    }

    void initMap(){
        this.inDegreeMap = new HashMap<>();
        for(NodePO nodePO:this.nodeList){
            this.inDegreeMap.put(nodePO.getNodeID(),0);
        }
        for(EdgePO edgePO : this.edgePOList) {
            Long key = edgePO.getTargetID();
            updateIndegreeMap(key,+1);
        }
    }

    //修改入度,变化值为off,可正可负
    void updateIndegreeMap(Long key,int off) {
        if(this.inDegreeMap.containsKey(key)) {
            int count = inDegreeMap.get(key) ;
            this.inDegreeMap.replace(key,count+off);
        }
    }

    void topologicalSort() {
        this.resultOutputList = new ArrayList<>();

        int count=0 ;
        //nodeID
        Queue<Long> queue = new LinkedList<>();

        //筛选0入读的顶点加入队列
        for(Map.Entry<Long,Integer> entry : inDegreeMap.entrySet()){
            if(entry.getValue()==0) {
                queue.offer(entry.getKey());
            }
        }

        while(!queue.isEmpty()){
            Long nodeID = queue.poll();

            //输出列表,输出列表时才有count++
            this.resultOutputList.add(nodeID);
            count++;
            if(count>=nodeList.size()) {
                break;
            }

            for (Long adjNodeID : getAdjNodeID(nodeID)) {
                //修改剩余节点的入度,如果修改后入度为0,入队列
                updateIndegreeMap(adjNodeID, -1);
                int value = this.inDegreeMap.get(adjNodeID);
                if (value == 0) {
                    queue.offer(adjNodeID);
                }
            }
        }

//        if(count != directedGraph.size())
//            throw new Exception("Graph has circle");
    }

    List<Long> getAdjNodeID(Long nodeID) {
        List<Long> result = new ArrayList<>();
        for(EdgePO edgePO:this.edgePOList){
            if(edgePO.getSourceID()==nodeID) {
                result.add(edgePO.getTargetID());
            }
        }
        return result;
    }



}
