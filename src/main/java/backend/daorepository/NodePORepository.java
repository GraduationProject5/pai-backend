package backend.daorepository;

import backend.model.po.NodePO;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface NodePORepository extends JpaRepository<NodePO,String> {
    List<NodePO> findByExperimentID(Long experimentID) ;

    NodePO findByNodeID(Long nodeID) ;

    NodePO findByNodeIDStr(String nodeIDStr) ;

    @Transactional
    void deleteByExperimentID(Long experimentID) ;

    /**
     * 根据node_no查找node信息
     *
     * @param nodeNo
     * @return
     */
    NodePO findByNodeNo(String nodeNo);

}
