package backend.daorepository;

import backend.model.po.Edge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EdgeRepository extends JpaRepository<Edge,String> {

    List<Edge> findByExperimentID(Long experimentID);
}
