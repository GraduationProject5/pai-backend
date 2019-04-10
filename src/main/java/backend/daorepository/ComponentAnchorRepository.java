package backend.daorepository;

import backend.model.po.ComponentAnchor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentAnchorRepository extends JpaRepository<ComponentAnchor,Integer> {

    List<ComponentAnchor> findByComponentID(int componentID);


}
