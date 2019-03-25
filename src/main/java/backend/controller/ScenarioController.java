package backend.controller;

import backend.service.ScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/scenario")
public class ScenarioController {

    @Autowired
    ScenarioService scenarioService ;





}
