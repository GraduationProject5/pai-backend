package backend.model.po;


import javax.persistence.*;
import javax.persistence.Table;
@Entity
@Table(name = "section", schema = "GraduationProject5")
public class Section {
    private int sectionID ;
    private String sectionName ;
    private int fatherSectionID ;

    public Section(){
    }

    public Section(String sectionName,int fatherSectionID){
        this.sectionName = sectionName ;
        this.fatherSectionID = fatherSectionID ;
    }

    @GeneratedValue
    @Id
    @Column(name= "section_id",nullable = false)
    public int getSectionID(){
        return this.sectionID ;
    }
    public void setSectionID(int newID) { this.sectionID = newID ; }


    @Basic
    @Column(name="section_name", nullable = false)
    public String getSectionName(){
        return this.sectionName ;
    }
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName ;
    }

    @Basic
    @Column(name = "father_section_id", nullable = false)
    public int getFatherSectionID(){
        return this.fatherSectionID ;
    }
    public void setFatherSectionID(int fatherSectionID){
        this.fatherSectionID = fatherSectionID ;
    }

}
