package backend.daorepository;

import backend.model.po.Node;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<Node,String> {

}
