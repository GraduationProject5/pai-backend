package backend.daorepository;

import backend.model.po.Edge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EdgeRepository extends JpaRepository<Edge,String> {
}
