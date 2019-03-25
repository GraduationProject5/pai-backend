package backend.model.po;


import javax.persistence.*;
import javax.persistence.Table;
@Entity
@Table(name = "r_user_experiment", schema = "GraduationProject5")
public class R_User_Experiment {
    private long rueID ;
    private long userID ;
    private long experimentID ;

    public R_User_Experiment(){
    }

    public R_User_Experiment(long userID,long experimentID){
        this.userID=userID;
        this.experimentID=experimentID;
    }

    @GeneratedValue
    @Id
    @Column(name= "rue_id",nullable = false)
    public long getRueID(){
        return this.rueID ;
    }
    public void setRueID(long newID) { this.rueID = newID ; }




    @Basic
    @Column(name="user_id", nullable = false)
    public long getUserID(){
        return this.userID ;
    }
    public void setUserID(long userID) {
        this.userID = userID ;
    }

    @Basic
    @Column(name = "experiment_id", nullable = false)
    public long getExperimentID(){
        return this.experimentID ;
    }
    public void setExperimentID(long experimentID){
        this.experimentID = experimentID ;
    }

}
