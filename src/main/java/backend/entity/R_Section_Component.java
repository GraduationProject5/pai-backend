package backend.entity;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "r_section_component", schema = "GraduationProject5")
public class R_Section_Component {
    private long rscID ;
    private int sectionID ;
    private int componentID ;

    public R_Section_Component(){
    }

    public R_Section_Component(int sectionID,int componentID){
        this.sectionID = sectionID ;
        this.componentID = componentID ;
    }

    @GeneratedValue
    @Id
    @Column(name= "rsc_id",nullable = false)
    public long getRscID(){
        return this.rscID ;
    }
    public void setRscID(long newID) { this.rscID = newID ; }


    @Basic
    @Column(name="section_id", nullable = false)
    public int getSectionID(){
        return this.sectionID ;
    }
    public void setSectionID(int sectionID) {
        this.sectionID = sectionID ;
    }

    @Basic
    @Column(name = "component_id", nullable = false)
    public int getComponentID(){
        return this.componentID ;
    }
    public void setComponentID(int componentID){
        this.componentID = componentID ;
    }


}
