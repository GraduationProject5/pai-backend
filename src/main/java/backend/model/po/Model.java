package backend.model.po;

import javax.persistence.*;
import javax.persistence.Table;


@Entity
@Table(name = "model", schema = "GraduationProject5")
public class Model {
    private long modelID ;
    private int firstComponentID ;
    private int secondComponentID ;

    public Model(){}

    public Model(int firstComponentID,int secondComponentID){
        this.firstComponentID=firstComponentID;
        this.secondComponentID=secondComponentID;
    }

    @Id
    @Column(name= "model_id",nullable = false)
    public long getModelID(){
        return this.modelID ;
    }
    public void setModelID(long newID) { this.modelID = newID ; }


    @Basic
    @Column(name="first_component_id", nullable = false)
    public int getFirstComponentID(){
        return this.firstComponentID ;
    }
    public void setFirstComponentID(int firstComponentID) {
        this.firstComponentID = firstComponentID ;
    }

    @Basic
    @Column(name = "second_component_id", nullable = false)
    public int getSecondComponentID(){
        return this.secondComponentID ;
    }
    public void setSecondComponentID(int secondComponentID) {
        this.secondComponentID = secondComponentID ;
    }


}
