package backend.model.po;


import backend.model.vo.NodeVO;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Map;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

@TypeDef(name = "json", typeClass = JsonStringType.class)
@Table(name = "nodes", schema = "GraduationProject5")
public class NodePO {

    @Id
    @GeneratedValue
    @Column(name = "node_id")
    private Long nodeID;        //建表主键

    @Basic
    @Column(name = "node_no")
    private String nodeNo ;         //"id"

    @Basic
    @Column(name = "render_index")
    private int index;

    @Basic
    @Column(name = "label")
    private String label;

    @Basic
    @Column(name = "node_name")
    private String name;

    @Basic
    @Column(name = "nodeid")
    private String nodeIDStr ;         //"nodeid"

    @Basic
    @Type( type = "json" )
    @Column(name = "settings_json",columnDefinition = "json")
    private Map<String,Object> settings ;

    @Basic
    @Column(name = "shape")
    private String shape;

    @Basic
    @Column(name = "size")
    private String size;

    @Basic
    @Column(name = "type")
    private String type;

    @Basic
    @Column(name = "x")
    private double x;

    @Basic
    @Column(name = "y")
    private double y;

    @Basic
    @Type( type = "json" )
    @Column(name = "anchor_json",columnDefinition = "json")
    private Map<String,Object> anchorJson ;

    @Basic
    @Column(name = "component_id")
    private int componentID;

    @Basic
    @Column(name = "experiment_id")
    private Long experimentID;      //long Long??

    /**
     * 从VO构造
     * @param nodeVO
     */
    public NodePO(NodeVO nodeVO,int componentID,Long experimentID){
        setIndex(nodeVO.index);
        setName(nodeVO.name);
        setLabel(nodeVO.label);
        setNodeNo(nodeVO.id);
        setNodeIDStr(nodeVO.nodeid);
        setShape(nodeVO.shape);
        setType(nodeVO.type);
        setSize(nodeVO.size);
        setX(nodeVO.x);
        setY(nodeVO.y);
//        setAnchorJson(nodeVO.anchorJson);
        setSettings(nodeVO.settings);
        setComponentID(componentID);
        setExperimentID(experimentID);
    }

    /**
     * 返回VO
     */
    public NodeVO toNodeVO(){
        NodeVO nodeVO = new NodeVO();
        nodeVO.id=this.nodeNo;
        nodeVO.index=this.index;
        nodeVO.label=this.label;
        nodeVO.name=this.name;
        nodeVO.nodeid=this.nodeIDStr;
        nodeVO.settings=this.settings;
        nodeVO.shape=this.shape;
        nodeVO.size=this.size;
        nodeVO.type=this.type;
        nodeVO.x=this.x;
        nodeVO.y=this.y;
        return nodeVO;
    }
}
