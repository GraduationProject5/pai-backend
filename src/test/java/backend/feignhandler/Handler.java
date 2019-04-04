package backend.feignhandler;

public abstract class Handler {


    protected abstract void handle();

    // 执行
    public void execute(HandlerChain successor) {
        handle();
        if (successor != null) {
            successor.process();
        }
    }


    //继任者
    private Handler successor;

    public Handler getSuccessor() {
        return successor;
    }

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

}
