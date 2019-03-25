package backend.daorepository;

import backend.model.po.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperimentRepository extends JpaRepository<Experiment,Long> {
    Experiment findByExperimentID(long experimentID);
}
