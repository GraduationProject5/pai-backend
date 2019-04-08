package backend.daorepository;

import backend.model.po.DataResult;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface DataResultRepository extends JpaRepository<DataResult,Long> {
    List<DataResult> findByDataSetID(Long dataSetID);

    List<DataResult> findByExperimentID(Long experimentID);

    @Transactional
    void deleteByExperimentID(Long experimentID) ;

    @Transactional
    void deleteAllByDataSetID(Long dataSetID) ;
}
