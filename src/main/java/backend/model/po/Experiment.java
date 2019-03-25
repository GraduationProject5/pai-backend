package backend.model.po;


import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "experiment", schema = "GraduationProject5")
public class Experiment {

    private long experimentID ;
    private String experimentName ;
    private String description ;

    public Experiment(){
    }

    public Experiment(String experimentName,String description){
        this.experimentName = experimentName ;
        this.description = description ;
    }

    @GeneratedValue
    @Id
    @Column(name= "experiment_id",nullable = false)
    public long getExperimentID(){
        return this.experimentID ;
    }
    public void setExperimentID(long newID) { this.experimentID = newID ; }


    @Basic
    @Column(name="experiment_name", nullable = false)
    public String getExperimentName(){
        return this.experimentName ;
    }
    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName ;
    }

    @Basic
    @Column(name = "description", nullable = false)
    public String getDescription(){
        return this.description ;
    }
    public void setDescription(String description){
        this.description = description ;
    }

}
