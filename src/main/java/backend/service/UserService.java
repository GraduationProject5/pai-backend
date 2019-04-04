package backend.service;

/**
 * Created by lienming on 2019/1/17.
 */

//@Service
public interface UserService {

    /**
     * 成功，则返回 Token
     * 失败，则返回 错误码
     * login
     *
     * @param email
     * @param password
     * @return
     */
    String login(String email, String password);

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

    /**
     * 登出
     *
     * @param token
     */
    void logout(String token);

    /**
     * 根据token查找用户的id
     * @param token
     * @return
     */
    Long getUserIDByToken(String token);
}
