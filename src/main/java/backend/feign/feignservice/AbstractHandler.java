package backend.feign.feignservice;

import java.util.Map;

/**
 * 组件的抽象类
 */
public abstract class AbstractHandler {

//    protected abstract Map handleFeign();

    public abstract Map<String, Object> handleFeign(Map<String, Object> map);

//    public abstract Map<String, Object> handleFeign(String userID, String tableName);


    // 执行
    public void execute(HandlerChain successor, Map<String, Object> map) {

        // 调用handleFeign
        Map<String, Object> newMap = handleFeign(map);
//        newMap = handleFeign(map);
        if (successor != null) {
            successor.process(newMap);
        }
    }
}
