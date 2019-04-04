package backend.model.po;

import backend.model.vo.EdgeVO;
import lombok.*;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "edges", schema = "GraduationProject5")

public class EdgePO {

    @Id
    @GeneratedValue
    @Column(name = "edge_id")
    private Long edgeID ;

    @Basic
    @Column(name = "edge_no")
    private String edge_no;

    @Basic
    @Column(name = "render_index")
    private int index ;

    @Basic
    @Column(name = "source_id")
    private Long sourceID;

    @Basic
    @Column(name = "source_no")
    private String sourceNo;

    @Basic
    @Column(name = "source_anchor")
    private int sourceAnchor;

    @Basic
    @Column(name = "target_id")
    private Long targetID;

    @Basic
    @Column(name = "target_no")
    private String targetNo;

    @Basic
    @Column(name = "target_anchor")
    private int targetAnchor;

    @Basic
    @Column(name = "experiment_id")
    private Long experimentID;

    /**
     * 从VO构造
     * @param
     */
    public EdgePO(EdgeVO edgeVO,Long experimentID){
        setIndex(edgeVO.index);
        setEdge_no(edgeVO.id);
        setSourceNo(edgeVO.source);
        setSourceAnchor(edgeVO.sourceAnchor);
        setTargetNo(edgeVO.target);
        setTargetAnchor(edgeVO.targetAnchor);
        setExperimentID(experimentID);
    }

    /**
     * 生成VO返回
     */
    public EdgeVO toEdgeVO(){
        EdgeVO edgeVO = new EdgeVO();
        edgeVO.id=this.edge_no;
        edgeVO.index=this.index;
        edgeVO.source=this.sourceNo;
        edgeVO.target=this.targetNo;
        edgeVO.sourceAnchor=this.sourceAnchor;
        edgeVO.targetAnchor=this.targetAnchor;
        return edgeVO;
    }
}
