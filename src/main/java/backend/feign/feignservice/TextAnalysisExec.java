package backend.feign.feignservice;

import backend.feign.feignclient.TextAnalysisFeign;
import backend.service.DataService;
import backend.util.config.StaticVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TextAnalysisExec {

    @Autowired
    private TextAnalysisFeign textAnalysisFeign;
    @Autowired
    private DataService dataService;

    public class Participles extends AbstractHandler {
        /**
         * 已测试
         * <p>
         * 分词
         *
         * @param map
         * @return{ "seg_list": [
         * "我",
         * "来说",
         * "两句",
         * "广州",
         * ]
         * }
         */
        @Override
        //TODO @SessionAttribute("userID") String userID,
        //     @RequestParam("tableName") String tableName
        public Map<String, Object> handleFeign(Map<String, Object> map) {
            //获取表中的内容
            String temp = map.get("texts").toString();
            String[] tempSplit = temp.split(",");
            String content = tempSplit[0];//获取内容
            Map<String, Object> tmpMap = new HashMap<>();
            tmpMap.put("text", content);
            return textAnalysisFeign.participles(tmpMap);
        }
    }

    public class StopwordsFilter extends AbstractHandler {
        /**
         * 已测试
         * <p>
         * 停词过滤
         *
         * @param map
         * @return "stopped_tokens": [
         * "我",
         * "来说",
         * "两句",
         * "广州",
         * ]
         * }
         */
//        @GetMapping(value = "getStopwordsFilter")
        @Override
        public Map<String, Object> handleFeign(Map<String, Object> map) {
            Map<String, Object> swMap = new LinkedHashMap<>();
            swMap.put("stop_list", StaticVariable.stop_words);
            swMap.put("seg_list", map.get("seg_list"));
            return textAnalysisFeign.stopwords_filter(swMap);
        }
    }

    public class FrequencyStatistics extends AbstractHandler {
        /**
         * 词频
         *
         * @param map
         * @return "kvs": {
         * "0": {
         * "0": 1,
         * },
         * "1": {
         * "0": 1,
         * "1": 1,
         * "2": 1,
         * },
         * "2": {
         * "0": 1,
         * "1": 1,
         * }
         * }
         */
        @GetMapping(value = "getFrequencyStatistics")
        public Map<String, Object> handleFeign(Map<String, Object> map) {
            return textAnalysisFeign.frequency_statistics(map);
        }
    }

    public class LDA extends AbstractHandler {
        /**
         * LDA
         *
         * @param map
         * @return "docres": [
         * [
         * 0.013218699896100949,
         * 0.013218699896421975,
         * 0.013218699896285793,
         * 0.9603439003111912
         * ]
         * }
         */
        @GetMapping(value = "getLDA")
        public Map<String, Object> handleFeign(Map<String, Object> map) {
            return textAnalysisFeign.LDA(map);
        }
    }
}
