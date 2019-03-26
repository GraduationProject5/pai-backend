package backend.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 文本分析组件
 * - 分词
 * - 停词过滤
 * - 词频统计
 * - LDA
 */
@Service
@CrossOrigin
@FeignClient(url = "${ml.feign.url}", name = "algorithm")
public interface TextAnalysisFeign {

    /**
     * 分词
     *
     * @param text
     * @return
     */
    @RequestMapping(value = "/par/", method = RequestMethod.POST)
    Map getParticiples(@RequestBody Map text);

//    @RequestMapping(value = "/sw", method = RequestMethod.POST)
//    void getStopwordsFilter();
//
//    void getFrequencyStatistics();
//
//    void getLDA();
}
