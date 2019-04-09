package backend.daorepository;

import backend.model.po.DataSet;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface DataSetRepository extends JpaRepository<DataSet,Long> {

    List<DataSet> findByUserID(Long userID);

    List<DataSet> findByNodeNo(String nodeNo);

    @Transactional
    void deleteAllByNodeNo(String nodeNo);

    DataSet findByUserIDAndExperimentIDAndNodeNo(Long userID,Long experimentID,String nodeNo);

    List<DataSet> findByExperimentID(Long experimentID);

    @Transactional
    void deleteByExperimentID(Long experimentID) ;




}
