package backend.feign;

import backend.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param userID
     * @param tableName
     * @return
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

    @GetMapping(value = "getStopwordsFilter")
    public Map getStopwordsFilter(@RequestBody Map<String, Object> map) {
        return textAnalysisFeign.stopwords_filter(map);
    }

    @GetMapping(value = "getFrequencyStatistics")
    public Map getFrequencyStatistics(@RequestBody Map<String, Object> map) {
        return textAnalysisFeign.frequency_statistics(map);
    }

    @GetMapping(value = "getLDA")
    public Map getLDA(@RequestBody Map<String, Object> map) {
        return textAnalysisFeign.LDA(map);
    }
}
