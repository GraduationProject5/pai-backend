package backend.model.po;


import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Map;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Table(name = "dataresults", schema = "GraduationProject5")
public class DataResult {

    @Id
    @GeneratedValue
    @Column(name = "dataresult_id")
    private Long dataResultID;

    //存储组件的结果
    @Basic
    @Type( type = "json" )
    @Column(name = "data_json",columnDefinition = "json")
    private Map<String,Object> data ;

    @Basic
    @Column(name = "dataset_id")
    private Long dataSetID;

    @Basic
    @Column(name = "experiment_id")
    private Long experimentID;

    /**
     * copy
     */
    public DataResult(DataResult dataResult,Long dataSetID,Long experimentID){
        setExperimentID(experimentID);
        setData(dataResult.data);
        setDataSetID(dataSetID);
    }
}
