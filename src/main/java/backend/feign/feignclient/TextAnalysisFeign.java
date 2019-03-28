package backend.feign.feignclient;

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
     * @return {
     * "seg_list": [
     * "我",
     * "来说",
     * "两句",
     * "广州",
     * ]
     * }
     */
    @PostMapping(value = "/par/")
    Map<String, Object> participles(@RequestBody Map text);


    /**
     * 停词过滤
     *
     * @param input
     * @return {
     * "stopped_tokens": [
     * "我",
     * "来说",
     * "两句",
     * "广州",
     * ]
     * }
     */
    @PostMapping(value = "/sw/")
    Map<String, Object> stopwords_filter(@RequestBody Map input);

    /**
     * 词频统计
     *
     * @param input
     * @return {
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
    @PostMapping(value = "/kv/")
    Map<String, Object> frequency_statistics(@RequestBody Map input);


    /**
     * LDA
     *
     * @param input
     * @return {
     * "docres": [
     * [
     * 0.013218699896100949,
     * 0.013218699896421975,
     * 0.013218699896285793,
     * 0.9603439003111912
     * ]
     * }
     */
    @PostMapping(value = "/lda/")
    Map<String, Object> LDA(@RequestBody Map input);
}
