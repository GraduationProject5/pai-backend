package backend.controller;

import backend.entity.User;
import backend.service.UserService;
import backend.util.JWThelper.JwtUtil;
import backend.util.register.email.EmailUtility;
//import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.TreeMap;

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
    @GetMapping(value = "/login")
    public String login(HttpSession session,
                        Model model,
                        @RequestParam("email") String email,
                        @RequestParam("password") String password) {

        String token = userService.login(email,password) ;
        if(token != null ) {
            model.addAttribute("token", token);
            return token ;
        }
        if(token == null) {
            model.addAttribute("result","账号密码错误");
        }

        return "Fail";

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(@RequestHeader("token")String token ) {
        userService.logout(token);
    }

    @PostMapping(value = "/sendEmail")
    public Map<String,Object> sendEmail(@RequestParam(value = "email") String email) {

        Map<String,Object> result = new TreeMap<>() ;

        boolean exist = userService.checkExist(email) ;

        if(exist){
            result.put("result",false);
            result.put("code", "-1");
        } else {
            String code = EmailUtility.sendAccountActivateEmail(email);
            result.put("result",true) ;
            result.put("code",code); //用于验证
        }

        return result;
    }

    @PostMapping(value = "/register")
    public Map<String,Object> register(@RequestParam(value = "email") String email,
                                        @RequestParam(value = "password") String password) {
                        //@RequestParam(value = "code") String code
        Map<String,Object> result = new TreeMap<>() ;

        long query_userid = userService.register(email,password) ;

        System.out.println("register result: " +query_userid) ;

        int query_int = (int)query_userid;
        switch ( query_int ) {
            case -1:
                result.put("result",false) ;
                result.put("message", "email illegal");
                return result ;
            case -2:
                result.put("result",false) ;
                result.put("message", "email exist");
                return result ;
            case -3:
                result.put("result",false) ;
                result.put("message", "password illegal");
                return result ;
            default:
//                EmailUtility.sendAccountActivateEmail("javalem@163.com",query_userid+"");
//                String code = EmailUtility.sendAccountActivateEmail(email);
                result.put("result",true) ;
//                result.put("code",code); //用于验证
                result.put("message", "success");
                return result;
        }





    }





}