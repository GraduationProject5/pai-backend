package backend.model.po;


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
@Table(name = "component", schema = "GraduationProject5")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Component {

    @GeneratedValue
    @Id
    @Column(name= "component_id")
    private int componentID;

    @Basic
    @Column(name="component_name", nullable = false)
    private String componentName ;
    @Basic
    @Column(name="func_name", nullable = false)
    private String funcName;
    //存储组件的结果
    @Basic
    @Type( type = "json" )
    @Column(name = "settings",columnDefinition = "json")
    private Map<String,Object> settings ;


}
