package backend.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

public interface TextAnalysisService {
    /**
     * 分词
     *
     * @param text
     * @return
     */
    Map getParticiples(  Map text);
}
