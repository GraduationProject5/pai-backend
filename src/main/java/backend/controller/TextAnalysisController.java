package backend.controller;


import backend.service.TextAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/text-analysis")
public class TextAnalysisController {

    @Autowired
    private TextAnalysisService textAnalysisService;

    @RequestMapping(value = "/participles")
    public void getParticiples() {
        
    }

}
