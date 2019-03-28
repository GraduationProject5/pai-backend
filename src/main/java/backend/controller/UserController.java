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

        String token = userService.login(email,password) ;
        Map<String,Object> result = HttpResponseHelper.newResultMap();


        if(token.charAt(0) != '-' ) {   //...不知道怎么判断了
            //TODO 需要前端把token加入HttpRequest Header吗?
            session.setAttribute("userID", userService.getUserIDByToken(token)  );
            result.put("result",true);
            result.put("token",token) ;
        }
        else {
            result.put("result",false);
            result.put("message",token);
        }

        return result;
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
            result.put("code", LoginProperties.Code_EmailRegistered);
        } else {
            String code = EmailUtility.sendAccountActivateEmail(email);
            result.put("result",true) ;
            result.put("code",code); //交给前端验证
        }

        return result;
    }

    @PostMapping (value = "/register")
    public Map<String,Object> register(@RequestParam(value = "email") String email,
                                        @RequestParam(value = "password") String password) {

        Map<String,Object> result = HttpResponseHelper.newResultMap();

        long query_userid = userService.register(email,password) ;

//        System.out.println("register result: " +query_userid) ;

        int queryID = (int)query_userid;
        switch ( queryID ) {

            case LoginProperties.Code_EmailRegistered:
                result.put("result",false) ;
                result.put("message", LoginProperties.Code_EmailRegistered );
                return result ;

            default:
                result.put("result",true) ;
                result.put("userID", queryID );
                return result;
        }





    }





}