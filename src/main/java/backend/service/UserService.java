package backend.service;

import backend.entity.User;

/**
 * Created by lienming on 2019/1/17.
 */
public interface UserService {

    /**
     * login
     *
     * @param email
     * @param password
     * @return
     */
    User login(String email, String password);

    /**
     * register
     * @param email
     * @param password
     * @return
     */
    long register(String email, String password);

    /**
     * 验证存在
     * @param email
     * @return
     */
    boolean checkExist(String email);
}
