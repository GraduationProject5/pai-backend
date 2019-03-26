package backend.feign;

import backend.service.DataService;
import backend.feign.TextAnalysisFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/text-analysis")
public class TextAnalysisService {

    @Autowired
    private TextAnalysisFeign textAnalysisFeign;
    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/participles")
    public Map getParticiples(@SessionAttribute("userID") String userID,
                              @RequestParam("tableName") String tableName) {
        //获取表中的row
        List tableList = dataService.getData(Long.parseLong(userID), tableName);
        Map mapText = new HashMap<>();
        mapText.put("text", ((Map) tableList.get(0)).get("text"));
        return textAnalysisFeign.getParticiples(mapText);
    }
}
