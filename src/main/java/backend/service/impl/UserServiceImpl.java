package backend.service.impl;

import backend.dao.UserRepository;
import backend.entity.User;
import backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lienming on 2019/1/17.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User login(String email, String password) {

        User user = userRepository.findByEmailAndPassword(email,password) ;

        if( null == user )  return null ;

        return user;
    }

    //TODO
    public int register(String email, String password) {
        return 0;
    }
}
