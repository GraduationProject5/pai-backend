package backend.service;

import backend.entity.User;

/**
 * Created by lienming on 2019/1/17.
 */
public interface UserService {
    User login(String email, String password);

    long register(String email, String password);
}
