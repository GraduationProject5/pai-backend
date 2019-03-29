package backend.util.json;

import backend.model.vo.EdgeVO;
import backend.model.vo.NodeVO;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class JSONHelper {

    public static List<NodeVO> toNodeVOList(List<NodeVO> list){
        List<NodeVO> result = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            String jsonStr = JSON.toJSONString(list.get(i));
            NodeVO vo = JSON.parseObject(jsonStr,NodeVO.class);
            result.add(vo);
        }
        return result;
    }

    public static List<EdgeVO> toEdgeVOList(List<EdgeVO> list){
        List<EdgeVO> result = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            String jsonStr = JSON.toJSONString(list.get(i));
            EdgeVO vo = JSON.parseObject(jsonStr,EdgeVO.class);
            result.add(vo);
        }
        return result;
    }

}

//    public static Map convertToMap(JSONObject object){
//        Map result = new HashMap<>();
//        Set<Map.Entry> sets = object.entrySet();
//
//        for( Map.Entry set : sets ){
//            result.put(set.getKey(),set.getValue());
//        }
//        return result ;
//    }
