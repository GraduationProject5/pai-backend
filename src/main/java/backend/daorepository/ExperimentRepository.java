package backend.daorepository;

import backend.entity.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperimentRepository extends JpaRepository<Experiment,Long> {
    Experiment findByExperimentID(long experimentID);
}
