package backend.feign.feignhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试类
 */
public class MainExec {

    protected static class Part1 extends Handler {
        @Override
        protected void handle() {
            System.out.println("Part1的handle");
        }
    }

    protected static class Part2 extends Handler {
        @Override
        protected void handle() {
            System.out.println("Part2的handle");
        }
    }

    public static class Part3 extends Handler {
        @Override
        protected void handle() {
            System.out.println("Part3的handle");
        }
    }

//    public static class Part4 extends Handler2 {
//        @Override
//        protected void handle() {
//            System.out.println("Part4的handle");
//        }
//    }

    /**
     * main
     *
     * @param args
     */
    public static void test(String[] args) {
//        Part1 part1 = new Part1();
//        Part2 part2 = new Part2();
//        Part3 part3 = new Part3();
        List<Handler> successors = new ArrayList<>();
//        successors.add(part1);
//        successors.add(part2);
//        successors.add(part3);

        List<String> list = new ArrayList<>(Arrays.asList("part1", "part5", "part2", "part3", "part4"));
        for (String str :
                list) {
            switch (str) {
                case "part1":
                    Part1 part1 = new Part1();
                    successors.add(part1);
                    break;
                case "part2":
                    Part2 part2 = new Part2();
                    successors.add(part2);
                    break;
                case "part3":
                    Part3 part3 = new Part3();
                    successors.add(part3);
                    break;
//                case "part4":
//                    Part4 part4 = new Part4();
//                    successors.add(part4);
                default:
                    System.out.println("Sorry, I dont know.");
            }
        }

        HandlerChain handlerChain = new HandlerChain(successors);
        handlerChain.process();
    }
}

