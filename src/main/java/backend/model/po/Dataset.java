package backend.model.po;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dataset", schema = "GraduationProject5")
public class Dataset {

    @Id
    @Column(name = "dataset_id")
    private Long datasetID;

    @Basic
    @Column(name = "data_str")
    private String dataStr ;

    @Basic
    @Column(name = "user_id")
    private Long userID;

    @Basic
    @Column(name = "experiment_id")
    private Long experimentID;

    @Basic
    @Column(name = "node_id")
    private Long nodeID;


}
