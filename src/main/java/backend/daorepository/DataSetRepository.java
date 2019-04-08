package backend.daorepository;

import backend.model.po.DataSet;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface DataSetRepository extends JpaRepository<DataSet,Long> {

    List<DataSet> findByUserID(Long userID);

    List<DataSet> findByNodeID(Long nodeID);

    @Transactional
    void deleteAllByNodeID(Long nodeID);

    DataSet findByUserIDAndExperimentIDAndNodeID(Long userID,Long experimentID,Long nodeID);

    List<DataSet> findByExperimentID(Long experimentID);

    @Transactional
    void deleteByExperimentID(Long experimentID) ;




}
