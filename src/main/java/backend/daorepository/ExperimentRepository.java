package backend.daorepository;

import backend.model.po.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ExperimentRepository extends JpaRepository<Experiment,Long> {
    Experiment findByExperimentID(Long experimentID);

    @Transactional
    void deleteByExperimentID(Long experimentID);
}
