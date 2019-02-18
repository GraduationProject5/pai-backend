package backend.controller;

import backend.entity.User;
import backend.service.UserService;
import backend.util.register.email.EmailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lienming on 2019/1/17.
 */
@Controller
//@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService ;

    //test
    @RequestMapping(value = "/test")
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
                        @RequestParam("email") String email,
                        @RequestParam("password") String password) {

        User user = userService.login(email,password) ;
        if(user != null ) {
//            session.setAttribute(SystemDefault.USER_ID,query_userid);
//            String userName = userService.getUserInfo(query_userid).getUserName() ;
            session.setAttribute("email",user.getEmail());
            session.setAttribute("userID",user.getUserID());
            return "redirect:/member/index" ;
//            return "success!user:"+user.getEmail();
        }
        if(user == null) {
            model.addAttribute("result","账号密码错误");
        }

        return "user/error";

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String doManagerLogout(HttpSession session) {
        session.removeAttribute("email");
        return "user/login";
    }

    @PostMapping(value = "/sendEmail")
    @ResponseBody
    public Map<String,Object> sendEmail(@RequestParam(value = "email") String email) {

        Map<String,Object> result = new TreeMap<>() ;

        boolean exist = userService.checkExist(email) ;

        if(exist){
            result.put("result",false);
            result.put("message", "email exist");
        } else {
            String code = EmailUtility.sendAccountActivateEmail(email);
            result.put("result",true) ;
            result.put("code",code); //用于验证
        }

        return result;
    }

    @PostMapping(value = "/register")
    @ResponseBody
    public Map<String,Object> register(@RequestParam(value = "email") String email,
                                        @RequestParam(value = "password") String password) {

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
                return result;
        }


    }





}