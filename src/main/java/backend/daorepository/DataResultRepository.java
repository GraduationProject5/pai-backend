package backend.daorepository;

import backend.model.po.DataResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataResultRepository extends JpaRepository<DataResult,Long> {
    List<DataResult> findByDataSetID(Long dataSetID);
}