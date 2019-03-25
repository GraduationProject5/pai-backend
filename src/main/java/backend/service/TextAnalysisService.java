package backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 文本分析组件
 * - 分词
 * - 停词过滤
 * - 词频统计
 * - LDA
 */
@Service
@FeignClient(url = "${ml.feign.url}", name = "algorithm")
public interface TextAnalysisService {

    @RequestMapping(value = "/par", method = RequestMethod.POST)
    void getParticiples();
//    @RequestMapping(value = "/sw", method = RequestMethod.POST)
//    void getStopwordsFilter();
//
//    void getFrequencyStatistics();
//
//    void getLDA();
}