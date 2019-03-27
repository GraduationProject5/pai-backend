package backend.daorepository;

import backend.model.po.NodePO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NodePORepository extends JpaRepository<NodePO,String> {
    List<NodePO> findByExperimentID(Long experimentID) ;

    NodePO findByNodeID(Long nodeID) ;
}
