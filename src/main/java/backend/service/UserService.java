package backend.service;

import java.util.List;
import java.util.Map;

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
    Map<String,Object> login(String email, String password);

    /**
     * register
     * @param email
     * @param password
     * @return
     */
    Map<String,Object> register(String email, String password);

    /**
     * 验证存在
     * @param email
     * @return
     */
    boolean checkExist(String email);

    Map<String,Object> sendEmail(String email);

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

    /**
     * 根据id查找用户名
     *
     * @param userID
     * @return
     */
    String getUserNameByUserID(long userID);
}
