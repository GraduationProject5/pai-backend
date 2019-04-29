package backend.controller;

import backend.service.UserService;
import backend.util.config.LoginProperties;
import backend.util.json.HttpResponseHelper;
import backend.util.register.email.EmailUtility;
//import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public Map<String,Object> login(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    HttpSession session,
                                    @RequestParam("email") String email,
                                    @RequestParam("password") String password) {
        Cookie[] cookies = httpServletRequest.getCookies();
        String token = null;
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                    if(token!=null){
                        Map<String,Object> result = HttpResponseHelper.newResultMap();
                        result.put("result",false);
                        result.put("message","已登录");
                        return result;
                    }
                }
            }
        }

        Map<String,Object> map = userService.login(email,password);
        boolean loginSuccess = (boolean)map.get("result");
        if(loginSuccess) {
            token = (String) map.get("token");
            Cookie cookie = new Cookie("token",token);
            cookie.setMaxAge(3600);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
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