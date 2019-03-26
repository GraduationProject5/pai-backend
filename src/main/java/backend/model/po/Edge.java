package backend.model.po;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "edges", schema = "GraduationProject5")
@Getter
@Setter
//@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Edge   {

    @Id
    @Column(name = "edge_id")
    private Long edgeID ;

    @Basic
    @Column(name = "edge_no")
    private String edge_no;

    @Basic
    @Column(name = "index")
    private int index ;

    @Basic
    @Column(name = "source_id")
    private String sourceID;

    @Basic
    @Column(name = "source_anchor")
    private int sourceAnchor;

    @Basic
    @Column(name = "target_id")
    private String targetID;

    @Basic
    @Column(name = "target_anchor")
    private int targetAnchor;

    @Basic
    @Column(name = "experiment_id")
    private Long experimentID;


}
