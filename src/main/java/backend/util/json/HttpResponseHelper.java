package backend.util.json;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponseHelper {

    public static Map<String,Object> newResultMap(){
        return new HashMap<>();
    }

    public static LinkedHashMap<String, Object> newLinkedResultMap() {
        return new LinkedHashMap<>();
    }
}
