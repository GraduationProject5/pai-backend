package backend.daorepository;

import backend.model.po.DataSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataSetRepository extends JpaRepository<DataSet,Long> {
    List<DataSet> findByUserID(Long userID);

    DataSet findByUserIDAndExperimentIDAndNodeID(Long userID,Long experimentID,Long nodeID);
}
