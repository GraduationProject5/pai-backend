package backend.daorepository;

import backend.entity.R_User_Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RUserTableRepository extends JpaRepository<R_User_Table,Long> {
    List<R_User_Table> findByUserID(long userID) ;



}
