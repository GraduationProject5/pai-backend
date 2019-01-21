package backend.dao;

import backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lienming on 2019/1/17.
 */

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmailAndPassword(String email,String password);
}
