package backend.feign.feignservice;

import backend.feign.feignclient.TextAnalysisFeign;
import backend.service.DataService;
import backend.util.config.StaticVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/text-analysis")
//暂时保留 RequestMapping 用来测试算法能否调用
@Service
public class TextAnalysisService {

    @Autowired
    private TextAnalysisFeign textAnalysisFeign;
    @Autowired
    private DataService dataService;

    /**
     *
     * 已测试
     *
     * 分词
     * @param userID
     * @param tableName
     * @return{
     *     "seg_list": [
     *         "我",
     *         "来说",
     *         "两句",
     *         "广州",
     *       ]
     *   }
     */
    @GetMapping(value = "getParticiples")
    public Map getParticiples(@SessionAttribute("userID") String userID,
                              @RequestParam("tableName") String tableName) {
        //获取表中的row
        List tableList = dataService.getData(Long.parseLong(userID), tableName);
        Map<String, Object> mapText = new HashMap<>();
        mapText.put("text", ((Map) tableList.get(0)).get("text"));
        return textAnalysisFeign.participles(mapText);
    }

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
    @GetMapping(value = "getStopwordsFilter")
    public Map getStopwordsFilter(@RequestBody Map<String, Object> map) {
        Map<String, Object> swMap = new LinkedHashMap<>();
//        List<String> swList = new ArrayList<>();
//        swList.add("；");
//        swList.add("、");
//        swList.add("的");
//        swList.add("。");
        swMap.put("stop_list", StaticVariable.stop_words);
        swMap.put("seg_list", map.get("seg_list"));
//        return swMap;
        return textAnalysisFeign.stopwords_filter(map);
    }

    /**
     * 词频
     * @param map
     * @return
     * "kvs": {
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
    public Map getFrequencyStatistics(@RequestBody Map<String, Object> map) {
        return textAnalysisFeign.frequency_statistics(map);
    }

    /**
     * LDA
     *
     * @param map
     * @return
     * "docres": [
     * [
     * 0.013218699896100949,
     * 0.013218699896421975,
     * 0.013218699896285793,
     * 0.9603439003111912
     * ]
     * }
     */
    @GetMapping(value = "getLDA")
    public Map getLDA(@RequestBody Map<String, Object> map) {
        return textAnalysisFeign.LDA(map);
    }
}
