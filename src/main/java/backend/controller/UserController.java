package backend.controller;

import backend.service.UserService;
import backend.util.config.LoginProperties;
import backend.util.json.HttpResponseHelper;
import backend.util.register.email.EmailUtility;
//import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by lienming on 2019/1/17.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService ;

    /**
        Web端调用后端需要登录的接口时在请求头中携带Token
     */
    @PostMapping(value = "/login")
    public Map<String,Object> login(HttpSession session,
                                    @RequestParam("email") String email,
                                    @RequestParam("password") String password) {
        Map<String,Object> map = userService.login(email,password);
        boolean loginSuccess = (boolean)map.get("result");
        if(loginSuccess) {
            String token = (String) map.get("token");
            session.setAttribute("token", token);
            session.setAttribute("userID", userService.getUserIDByToken(token));
        }

        return map;
    }

    //todo 使用存疑
    @PostMapping(value = "/logout")
    public void logout(HttpSession session,
                       @SessionAttribute("token")String token ) {
        userService.logout(token);
        session.removeAttribute("userID");
        session.removeAttribute("token");
    }

    @PostMapping(value = "/sendEmail")
    public Map<String,Object> sendEmail(@RequestParam(value = "email") String email) {
        return userService.sendEmail(email);
    }

    @PostMapping (value = "/register")
    public Map<String,Object> register(@RequestParam(value = "email") String email,
                                        @RequestParam(value = "password") String password) {
        return userService.register(email,password);
    }





}