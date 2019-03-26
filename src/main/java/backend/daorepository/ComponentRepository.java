package backend.daorepository;

import backend.model.po.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<Component,Integer> {

    Component findByComponentID(int componentID);
}
