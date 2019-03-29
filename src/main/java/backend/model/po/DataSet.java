package backend.model.po;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Table(name = "dataset", schema = "GraduationProject5")
public class DataSet {

    @Id
    @Column(name = "dataset_id")
    private Long dataSetID;

    //展示类型：表格"table"/其他


    @Basic
    @Column(name = "user_id")
    private Long userID;

    @Basic
    @Column(name = "experiment_id")
    private Long experimentID;

    @Basic
    @Column(name = "node_id")
    private Long nodeID;

    @Basic
    @Column(name = "type")
    private String type;


}
