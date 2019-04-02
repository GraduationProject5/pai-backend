package backend.daorepository;

import backend.model.po.TablePO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TablePORepository extends JpaRepository<TablePO,Long> {

    TablePO findByTableName(String tableName) ;

    TablePO findByTableID(Long tableID);

    void deleteByTableID(Long tableID);
}
