package backend.controller;

import backend.entity.User;
import backend.service.UserService;
import backend.util.register.email.EmailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lienming on 2019/1/17.
 */
@Controller
//test
public class UserController {

    @Autowired
    private UserService userService ;

    @RequestMapping(value = "/")
    @ResponseBody
    public String testUser() {
        User user = userService.login("javalem@163.com","asdasd");
        if(user==null)
            return "failed";
        else
            return "success";
    }


    @PostMapping(value = "/login")
    @ResponseBody
    public String login(HttpSession session, Model model,
                        @RequestParam("username") String email,
                        @RequestParam("password") String password) {

        User user = userService.login(email,password) ;
//        System.out.println(query_userid);
        if(user != null ) {
//            session.setAttribute(SystemDefault.USER_ID,query_userid);
//            String userName = userService.getUserInfo(query_userid).getUserName() ;
//            session.setAttribute("userName",userName);
//            return "redirect:/member/index" ;
            return "success!user:"+user.getEmail();
        }
        if(user == null) {
            model.addAttribute("error_msg","账号密码错误");
        }

        return "user/error";

    }

    @PostMapping(value = "/sendEmail")
    @ResponseBody
    public Map<String,Object> sendEmail(@RequestParam(value = "mail") String email,
                                        @RequestParam(value = "password") String password) {

        Map<String,Object> result = new TreeMap<>() ;

        int query_userid = userService.register(email,password) ;

        System.out.println("register result: " +query_userid) ;

        switch ( query_userid ) {
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
                String code = EmailUtility.sendAccountActivateEmail(email);
                result.put("result",true) ;
                result.put("code",code); //用于验证
                return result;
        }


    }



}