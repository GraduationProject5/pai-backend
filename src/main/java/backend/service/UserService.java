package backend.service;

import backend.entity.User;

/**
 * Created by lienming on 2019/1/17.
 */
public interface UserService {

    /**
     * 成功，则返回 Token
     * 失败，则返回 null
     * @param email
     * @param password
     * @return
     */
    String login(String email, String password);

    long register(String email, String password);

    boolean checkExist(String email);

    void logout(String token);
}
