package backend.daorepository;

import backend.entity.Texts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextsRepository extends JpaRepository<Texts, Long> {


}
