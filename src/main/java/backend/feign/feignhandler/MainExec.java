package backend.feign.feignhandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试类
 */
public class MainExec {

    private static class Part1 extends Handler {
        @Override
        protected void handle() {
            System.out.println("Part1的handle");
        }
    }

    private static class Part2 extends Handler {
        @Override
        protected void handle() {
            System.out.println("Part2的handle");
        }
    }

    private static class Part3 extends Handler {
        @Override
        protected void handle() {
            System.out.println("Part3的handle");
        }
    }

    public static void main(String[] args) {
        Part1 part1 = new Part1();
        Part2 part2 = new Part2();
        Part3 part3 = new Part3();
        List<Handler> successors = new ArrayList<>();
        successors.add(part1);
        successors.add(part2);
        successors.add(part3);

        HandlerChain subjectChain = new HandlerChain(successors);
        subjectChain.process();
    }
}

