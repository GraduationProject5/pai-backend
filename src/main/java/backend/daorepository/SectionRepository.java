package backend.daorepository;

import backend.model.po.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section,Integer> {
}
