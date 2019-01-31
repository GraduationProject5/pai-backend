package backend.entity;


import javax.persistence.*;

@Entity
@Table(name = "tablePO", schema = "GraduationProject5")
public class TablePO {
    private long tableID ;
    private String tableName ;
    private String description ;

    public TablePO(){
    }

    public TablePO(String tableName,String description){
        this.tableName = tableName ;
        this.description = description ;
    }

    @GeneratedValue
    @Id
    @Column(name= "table_id",nullable = false)
    public long getTableID(){
        return this.tableID ;
    }
    public void setTableID(long newID) { this.tableID = newID ; }


    @Basic
    @Column(name="table_name", nullable = false)
    public String getTableName(){
        return this.tableName ;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName ;
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
