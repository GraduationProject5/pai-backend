package backend.controller;

import backend.entity.User;
import backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lienming on 2019/1/17.
 */
@RestController
//test
public class UserController {

    @Autowired
    private UserService userService ;

    @RequestMapping(value = "/")
    public String testUser() {
        User user = userService.login("15105180709","asdasd");
        if(user==null)
            return "failed";
        else
            return "success";
    }


}