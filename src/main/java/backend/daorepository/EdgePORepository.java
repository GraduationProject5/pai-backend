package backend.daorepository;

import backend.model.po.EdgePO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EdgePORepository extends JpaRepository<EdgePO,String> {

    List<EdgePO> findByExperimentID(Long experimentID);


//    void deleteByExperimentID(Long experimentID) ;


}
