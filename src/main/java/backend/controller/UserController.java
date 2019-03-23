package backend.controller;

import backend.service.UserService;
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
    @GetMapping(value = "/login")
    public Map<String,Object> login(HttpSession session,
                        @RequestParam("email") String email,
                        @RequestParam("password") String password) {

        String token = userService.login(email,password) ;
        Map<String,Object> resultMap = HttpResponseHelper.newResultMap();

        if(token != null ) {

            //TODO 需要前端把token加入HttpRequest Header吗?
            session.setAttribute("userID", userService.getUserIDByToken(token)  );
            resultMap.put("token", token);

        }
        if(token == null) {
            resultMap.put("result","账号密码错误");
        }

        return resultMap;
    }

    @GetMapping(value = "/logout")
    public void logout(@RequestHeader("token")String token ) {
        userService.logout(token);
    }

    @GetMapping(value = "/sendEmail")
    public Map<String,Object> sendEmail(@RequestParam(value = "email") String email) {

        Map<String,Object> result = HttpResponseHelper.newResultMap();

        boolean exist = userService.checkExist(email) ;

        if(exist){
            //邮箱已注册
            result.put("result",false);
            result.put("code", "-1");
        } else {
            String code = EmailUtility.sendAccountActivateEmail(email);
            result.put("result",true) ;
            result.put("code",code); //交给前端验证
        }

        return result;
    }

    @GetMapping(value = "/register")
    public Map<String,Object> register(@RequestParam(value = "email") String email,
                                        @RequestParam(value = "password") String password) {

        Map<String,Object> result = HttpResponseHelper.newResultMap();

        long query_userid = userService.register(email,password) ;

//        System.out.println("register result: " +query_userid) ;

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
                result.put("result",true) ;
                result.put("message", "success");
                return result;
        }





    }





}