package backend.daorepository;

import backend.model.po.R_User_Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RUserTableRepository extends JpaRepository<R_User_Table,Long> {
    List<R_User_Table> findByUserID(long userID) ;

    R_User_Table findByTableID(Long tableID) ;

}
