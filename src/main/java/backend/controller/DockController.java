package backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockController {
    @RequestMapping(value = "/docker")
    public String test() {
        return "Hello Docker!";
    }
}
