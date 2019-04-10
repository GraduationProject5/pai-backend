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

/**
 * 根据算法，一个组件只能一个输入一个输出，所以锚点现在用不上，交给前端处理了
 */
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Table(name = "component_anchor", schema = "GraduationProject5")
public class ComponentAnchor {
    @Id
    @GeneratedValue
    @Column(name = "anchor_id")
    private int anchorID ;

    @Basic
    @Column(name = "component_id")
    private int componentID ;

    @Basic
    @Column(name = "x_off")
    private double x_off;

    @Basic
    @Column(name = "y_off")
    private double y_off;

    @Basic
    @Type( type = "json" )
    @Column(name = "type",columnDefinition = "json")
    private Map<String,String> type;

}
