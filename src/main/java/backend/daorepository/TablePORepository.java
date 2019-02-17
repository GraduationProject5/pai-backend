package backend.daorepository;

import backend.entity.TablePO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TablePORepository extends JpaRepository<TablePO,Long> {

    TablePO findByTableName(String tableName) ;

    TablePO findByTableID(long tableID);
}
