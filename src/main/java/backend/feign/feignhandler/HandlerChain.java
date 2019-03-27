package backend.feign.feignhandler;

import java.util.List;

/**
 * handler 中间类
 */
public class HandlerChain {

    private List<Handler> successors;

    public HandlerChain(List<Handler> successors) {
        this.successors = successors;
    }

    private int index = 0;

    public void process() {
        if (index < successors.size()) {
            successors.get(index++).execute(this);
        }
    }

}
