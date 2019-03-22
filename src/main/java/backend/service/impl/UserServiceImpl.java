package backend.service.impl;

import backend.daorepository.UserRepository;
import backend.entity.User;
import backend.service.UserService;
import backend.util.JWThelper.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
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

        if( null == user ) return null ;

        String token = JwtUtil.generateToken
                (user.getUserID()+"","PAI-back end","user");

        this.loginID_list.add(token);

        System.out.println(token);
        return token;
    }

    public void logout(String token) {
        this.loginID_list.remove(token);
    }

    //TODO
    public long register(String email, String password) {
        User tmp = userRepository.findByEmailAndPassword(email,password) ;
        if( null != tmp )
            return -1; //
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
