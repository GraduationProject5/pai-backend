package backend.util.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaticVariable {
    //停词过滤
    public static List<String> stop_words = new ArrayList<>(Arrays.asList("；", "、", "的", "。"));
}
