package backend.model.po;


import javax.persistence.*;
import javax.persistence.Table;
@Entity
@Table(name = "component", schema = "GraduationProject5")
public class Component {
    private int componentID ;
    private String componentName ;

    public Component(){
    }

    public Component(String componentName){
        this.componentName = componentName ;
    }

    @GeneratedValue
    @Id
    @Column(name= "component_id",nullable = false)
    public int getComponentID(){
        return this.componentID ;
    }
    public void setComponentID(int newID) { this.componentID = newID ; }


    @Basic
    @Column(name="component_name", nullable = false)
    public String getComponentName(){
        return this.componentName ;
    }
    public void setComponentName(String componentName) {
        this.componentName = componentName ;
    }

}
