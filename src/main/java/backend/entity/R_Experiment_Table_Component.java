package backend.entity;


import javax.persistence.*;
import javax.persistence.Table;
@Entity
@Table(name = "r_experiment_table_component", schema = "GraduationProject5")
public class R_Experiment_Table_Component {
    private long retcID ;
    private long experimentID ;
    private int firstComponentID ;
    private int secondComponentID ;
    private String firstComponentType ;
    private String secondComponentType ;

    public R_Experiment_Table_Component(){}

    public R_Experiment_Table_Component
            (long experimentID,int firstComponentID, int secondComponentID,
             String firstComponentType, String secondComponentType){
        this.firstComponentID=firstComponentID;
        this.secondComponentID=secondComponentID;
        this.firstComponentType=firstComponentType;
        this.secondComponentType=secondComponentType;
    }

    @GeneratedValue
    @Id
    @Column(name= "retc_id",nullable = false)
    public long getRetcID(){
        return this.retcID ;
    }
    public void setRetcID(long newID) { this.retcID = newID ; }


    @Basic
    @Column(name="experiment_id", nullable = false)
    public long getExperimentID(){
        return this.experimentID ;
    }
    public void setExperimentID(int experimentID) {
        this.experimentID = experimentID ;
    }

    @Basic
    @Column(name = "first_component_id", nullable = false)
    public int getFirstComponentID(){
        return this.firstComponentID ;
    }
    public void setFirstComponentID(int firstComponentID){
        this.firstComponentID = firstComponentID ;
    }

    @Basic
    @Column(name = "second_component_id", nullable = false)
    public int getSecondComponentID(){
        return this.secondComponentID ;
    }
    public void setSecondComponentID(int secondComponentID){
        this.secondComponentID = secondComponentID ;
    }

    @Basic
    @Column(name = "first_component_type", nullable = false)
    public String getFirstComponentType(){
        return this.firstComponentType ;
    }
    public void setFirstComponentType(String firstComponentType){
        this.firstComponentType = firstComponentType ;
    }

    @Basic
    @Column(name = "second_component_type", nullable = false)
    public String getSecondComponentType(){
        return this.secondComponentType ;
    }
    public void setSecondComponentType(String secondComponentType){
        this.secondComponentType = secondComponentType ;
    }



}
