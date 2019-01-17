package backend.service;

import backend.entity.User;

/**
 * Created by lienming on 2019/1/17.
 */
public interface UserService {
    User login(String phone, String password);
}
