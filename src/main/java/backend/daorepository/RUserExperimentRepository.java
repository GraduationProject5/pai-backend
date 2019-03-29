package backend.daorepository;

import backend.model.po.R_User_Experiment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RUserExperimentRepository extends JpaRepository<R_User_Experiment,Long> {
    List<R_User_Experiment> findByUserID(long userID);

    void deleteByExperimentID(Long experimentID);
}
