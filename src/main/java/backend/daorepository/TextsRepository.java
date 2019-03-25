package backend.daorepository;

import backend.model.po.Texts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextsRepository extends JpaRepository<Texts, Long> {


}
