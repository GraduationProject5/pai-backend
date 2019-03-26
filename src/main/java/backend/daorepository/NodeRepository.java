package backend.daorepository;

import backend.model.po.Node;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NodeRepository extends JpaRepository<Node,String> {
    List<Node> findByExperimentID(Long experimentID) ;


}
