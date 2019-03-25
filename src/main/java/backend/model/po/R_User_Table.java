package backend.model.po;

import javax.persistence.*;
import javax.persistence.Table;
@Entity
@Table(name = "r_user_table", schema = "GraduationProject5")
public class R_User_Table {
    private long rutID ;
    private long userID ;
    private long tableID ;

    public R_User_Table(){
    }

    public R_User_Table(long userID,long tableID){
        this.userID=userID;
        this.tableID=tableID;
    }

    @GeneratedValue
    @Id
    @Column(name= "rut_id",nullable = false)
    public long getRutID(){
        return this.rutID ;
    }
    public void setRutID(long newID) { this.rutID = newID ; }


    @Basic
    @Column(name="user_id", nullable = false)
    public long getUserID(){
        return this.userID ;
    }
    public void setUserID(long userID) {
        this.userID = userID ;
    }

    @Basic
    @Column(name = "table_id", nullable = false)
    public long getTableID(){
        return this.tableID ;
    }
    public void setTableID(long tableID){
        this.tableID = tableID ;
    }

}
