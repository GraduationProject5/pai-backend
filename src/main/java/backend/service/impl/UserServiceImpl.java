package backend.service.impl;

import backend.daorepository.UserRepository;
import backend.model.po.User;
import backend.service.UserService;
import backend.util.JWThelper.JwtUtil;
import backend.util.config.LoginProperties;
import backend.util.json.HttpResponseHelper;
import backend.util.register.email.EmailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lienming on 2019/1/17.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;

//    private List<String> loginID_list = new ArrayList<>();


    public Map<String,Object> login(String email, String password) {

        Map<String,Object> result = HttpResponseHelper.newResultMap();
        String token ;
        User user = userRepository.findByEmailAndPassword(email,password) ;

        if( null == user ) {

            if( checkExist(email) ) {
                token = LoginProperties.Code_WrongPassword;  //-1
            }
            else {
                token = LoginProperties.Code_EmailNotExists; //-2
            }
            result.put("result",false);
            result.put("message",token);
            return result;
        }

        boolean hasLogin = JwtUtil.existUserID(user.getUserID()+"");

        if(hasLogin){
            token = LoginProperties.Code_HasLogin;
            result.put("result",false);
            result.put("message",token);
            return result;
        }

        token = JwtUtil.generateToken(user.getUserID()+"","PAI-back end","user");
        //每次都不一样.!
        JwtUtil.loginID_List.add(user.getUserID()+"");

        result.put("result",true);
        result.put("token",token) ;
        return result;
    }

    public Long getUserIDByToken(String token)  {
        try {
            return Long.parseLong(JwtUtil.getUserID(token));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1L;
    }

    public void logout(String token) {
        JwtUtil.removeTokenFromList(token);
    }

    public Map<String,Object> register(String email, String password) {
        Map<String,Object> result = HttpResponseHelper.newResultMap();

        User tmp = userRepository.findByEmailAndPassword(email,password) ;

        if( null != tmp ) {
            result.put("result",false) ;
            result.put("message", LoginProperties.Code_EmailRegistered );
            return result ;
        }

        User user = new User(email,password);
        int queryID = (int)userRepository.save(user).getUserID();
        result.put("result",true) ;
        result.put("userID", queryID );
        return result;

    }

    public Map<String,Object> sendEmail(String email) {
        Map<String,Object> result = HttpResponseHelper.newResultMap();
        boolean isExist = checkExist(email);
        if( isExist ) {
            //邮箱已注册
            result.put("result",false);
            result.put("code", LoginProperties.Code_EmailRegistered);
        }
        else {
            String code = EmailUtility.sendAccountActivateEmail(email);
            result.put("result",true) ;
            result.put("code",code); //交给前端验证
        }

        return result;
    }

    public boolean checkExist(String email){
        User tmp = userRepository.findByEmail(email) ;

        if( null != tmp ) {
            return true;  //exist
        } else {
            return false ; // not exist
        }
    }
}
