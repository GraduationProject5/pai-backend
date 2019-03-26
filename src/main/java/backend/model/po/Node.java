package backend.model.po;


import lombok.*;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "nodes", schema = "GraduationProject5")
@Getter
@Setter
//@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Node implements Serializable {

    @Id
    @Column(name = "node_id", nullable = false, length = 10)
    private String nodeID;

    @Basic
    @Column(name = "index")
    private int index;

    @Basic
    @Column(name = "label")
    private String label;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "nodeid")
    private int node_id ; //??????????

    @Basic
    @Column(name = "shape")
    private String shape;

    @Basic
    @Column(name = "type")
    private String type;

    @Basic
    @Column(name = "size")
    private String size;

    @Basic
    @Column(name = "x")
    private double x;

    @Basic
    @Column(name = "y")
    private double y;

    @Basic
    @Column(name = "experiment_id")
    private Long experimentID;      //long Long??


}
