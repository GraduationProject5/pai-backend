package backend.feign.feignhandler;

import java.util.ArrayList;
import java.util.Arrays;
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

//
//    public static void main(String[] args) {
////        Part1 part1 = new Part1();
////        Part2 part2 = new Part2();
////        Part3 part3 = new Part3();
//        List<Handler> successors = new ArrayList<>();
////        successors.add(part1);
////        successors.add(part2);
////        successors.add(part3);
//
//        List<String> list = new ArrayList<>(Arrays.asList("part1","part5","part2","part3","part5"));
//        for (String str :
//                list) {
//            switch (str) {
//                case "part1":
//                    MainExec.Part1 part1 = new MainExec.Part1();
//                    successors.add(part1);
//                    break;
//                case "part2":
//                    MainExec.Part2 part2 = new MainExec.Part2();
//                    successors.add(part2);
//                    break;
//                case "part3":
//                    MainExec.Part3 part3 = new MainExec.Part3();
//                    successors.add(part3);
//                    break;
//                default:
//                    System.out.println("Sorry, I dont know.");
//            }
//        }
//
//        HandlerChain handlerChain = new HandlerChain(successors);
//        handlerChain.process();
//    }

}
