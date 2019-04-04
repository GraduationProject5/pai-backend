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

    Map<String,Integer> inDegreeMap;

    List<NodePO> nodeList ;
    List<EdgePO> edgePOList;

    List<String> resultOutputList;

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
    public List<String> getExecuteOrder(){
        return this.resultOutputList;
    }

    public void getTopologyString(){
        for(String s : this.resultOutputList) {
            System.out.print(s+"  ");
        }
    }

    void initMap(){
        this.inDegreeMap = new HashMap<>();
        for(NodePO nodePO:this.nodeList){
            this.inDegreeMap.put(nodePO.getNodeNo(),0);
        }
        for(EdgePO edgePO : this.edgePOList) {
//            Long key = edgePO.getTargetID();
            String nodeNo = edgePO.getTargetNo();
            updateIndegreeMap(nodeNo,+1);
        }
    }

    //修改入度,变化值为off,可正可负
    void updateIndegreeMap(String key,int off) {
        if(this.inDegreeMap.containsKey(key)) {
            int count = inDegreeMap.get(key) ;
            this.inDegreeMap.replace(key,count+off);
        }
    }

    void topologicalSort() {
        this.resultOutputList = new ArrayList<>();

        int count=0 ;
        //nodeID
        Queue<String> queue = new LinkedList<>();

        //筛选0入读的顶点加入队列
        for(Map.Entry<String,Integer> entry : inDegreeMap.entrySet()){
            if(entry.getValue()==0) {
                queue.offer(entry.getKey());
            }
        }

        while(!queue.isEmpty()){
            String nodeNo = queue.poll();

            //输出列表,输出列表时才有count++
            this.resultOutputList.add(nodeNo);
            count++;
            if(count>=nodeList.size()) {
                break;
            }

            for (String adjNodeNo : getAdjNodeNo(nodeNo)) {
                //修改剩余节点的入度,如果修改后入度为0,入队列
                updateIndegreeMap(adjNodeNo, -1);
                int value = this.inDegreeMap.get(adjNodeNo);
                if (value == 0) {
                    queue.offer(adjNodeNo);
                }
            }
        }

//        if(count != directedGraph.size())
//            throw new Exception("Graph has circle");
    }

    List<String> getAdjNodeNo(String nodeNo) {
        List<String> result = new ArrayList<>();
        for(EdgePO edgePO:this.edgePOList){
            if(edgePO.getSourceNo().equals(nodeNo)){
                result.add(edgePO.getTargetNo());
            }
        }
        return result;
    }

}
