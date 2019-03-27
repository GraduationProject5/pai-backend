package backend.daorepository;

import backend.model.po.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatasetRepository extends JpaRepository<Dataset,Long> {

}
