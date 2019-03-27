package backend.daorepository;

import backend.model.po.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatasetRepository extends JpaRepository<Dataset,Long> {
    List<Dataset> findByUserID(Long userID);
}
