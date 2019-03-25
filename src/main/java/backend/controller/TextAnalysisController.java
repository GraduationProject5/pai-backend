package backend.controller;


import backend.dao.DatabaseHelper;
import backend.service.TextAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/text-analysis")
public class TextAnalysisController {

    @Autowired
    private TextAnalysisService textAnalysisService;
    @Autowired
    private DatabaseHelper databaseHelper;

    @RequestMapping(value = "/participles")
    public List<Map> getParticiples(@SessionAttribute("userID") String userID,
                                    @RequestParam("tableName") String tableName) {
        //获取表中的row
        List tableList = databaseHelper.getFromUserTable(Long.parseLong(userID), tableName);
        return tableList;
    }
}
