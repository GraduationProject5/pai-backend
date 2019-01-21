package backend.entity;

import javax.persistence.*;

/**
 * Created by lienming on 2019/1/17.
 */

@Entity
@Table(name = "user", schema = "GraduationProject5")
public class User {

    private int userID ;
    private String email ;
    private String password ;
    private boolean isActivated ;   //账号状态

    public User(){
    }

    public User(String email,String password){
        this.email = email ;
        this.password = password ;
        this.isActivated = false ;
    }

    @GeneratedValue
    @Id
    @Column(name= "user_id",nullable = false)
    public int getUserID(){
        return this.userID ;
    }
    public void setUserID(int newID) { this.userID = newID ; }


    @Basic
    @Column(name="phone", nullable = false, length = 11)
    public String getEmail(){
        return this.email ;
    }
    public void setEmail(String email) {
        this.email = email ;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 20)
    public String getPassword(){
        return this.password ;
    }
    public void setPassword(String password){
        this.password = password ;
    }

    @Basic
    @Column(name="activated", nullable = false)
    public boolean getActivated(){
        return this.isActivated ;
    }
    public void setActivated(boolean b){
        this.isActivated = b ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User entity = (User) o;

        if (userID != entity.userID) return false;
        if (email != null ? !email.equals(entity.email) : entity.email != null) return false;
        if (password != null ? !password.equals(entity.password) : entity.password != null) return false;
        if (isActivated != entity.isActivated) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + userID;
        return result;
    }
}
