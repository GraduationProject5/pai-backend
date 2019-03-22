package backend.util.json;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JSONHelper {


    public static Map convertToMap(JSONObject object){
        Map result = new HashMap<>();
        Set<Map.Entry> sets = object.entrySet();

        for( Map.Entry set : sets ){
            result.put(set.getKey(),set.getValue());
        }
        return result ;
    }


}
