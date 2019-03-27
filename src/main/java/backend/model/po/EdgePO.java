package backend.model.po;

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


}
