package backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class WelcomeController {

    @GetMapping({"/", "/index"})
    public String index(Model model, @RequestParam(value = "name", required = false, defaultValue = "PMLS") String name) {
        model.addAttribute("name", name);
        return "index";
    }
}
