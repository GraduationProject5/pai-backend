package backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockController {

    @Value("${spring.datasource.username}")
    private String name;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @RequestMapping(value = "/docker")
    public String test() {
        return name + " " + password + " " + driver + " 12000oooop00---000";
    }
}
