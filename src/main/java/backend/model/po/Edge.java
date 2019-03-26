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

public class Edge implements Serializable {

    private String edgeID ;
    private int index ;
    private String sourceID;
    private int sourceAnchor;
    private String targetID;
    private int targetAnchor;
    private long experimentID;


}
