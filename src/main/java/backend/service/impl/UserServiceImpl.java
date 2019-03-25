package backend.service.impl;

import backend.daorepository.UserRepository;
import backend.model.po.User;
import backend.service.UserService;
import backend.util.JWThelper.JwtUtil;
import backend.util.config.LoginProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lienming on 2019/1/17.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;

    private List<String> loginID_list = new ArrayList<>();

    public String login(String email, String password) {

        User user = userRepository.findByEmailAndPassword(email,password) ;

        if( null == user ) {
            if(checkExist(email)) {
                return LoginProperties.Code_WrongPassword;  //-1
            }
            else
                return LoginProperties.Code_EmailNotExists; //-2
        }
        String token = JwtUtil.generateToken
                (user.getUserID()+"","PAI-back end","user");

        this.loginID_list.add(token);

//        System.out.println(token);
        return token;
    }

    public String getUserIDByToken(String token)  {
        try {
            return JwtUtil.getUserID(token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "-1";
    }

    public void logout(String token) {
        this.loginID_list.remove(token);
    }

    public long register(String email, String password) {
        User tmp = userRepository.findByEmailAndPassword(email,password) ;
        if( null != tmp )
            return LoginProperties.Code_EmailRegistered;
        User user = new User(email,password);
        return userRepository.save(user).getUserID();
    }

    public boolean checkExist(String email) {
        User tmp = userRepository.findByEmail(email);
        if( null != tmp )
            return true;
        else
            return false;
    }
}
