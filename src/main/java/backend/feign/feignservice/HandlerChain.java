package backend.feign.feignservice;


import java.util.List;
import java.util.Map;

/**
 * handle 中间类
 * <p>
 * 将所有的后继者对象填装在一个 List 内，
 * 方便交给调用 execute 处理
 */

public class HandlerChain {

    private List<AbstractHandler> successors;

    public HandlerChain(List<AbstractHandler> successors) {
        this.successors = successors;
    }

    private int index = 0;

    public void process(Map<String, Object> map) {
        if (index < successors.size()) {
            successors.get(index++).execute(this, map);
        }
    }
}