package backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * Created by lienming on 2019/1/17.
 */

@Entity
//@Getter
//@Builder
@Table(name = "user", schema = "GraduationProject5")
public class User {

    private long userID ;
    private String email ;
    private String password ;

    public User(){
    }

    public User(String email,String password){
        this.email = email ;
        this.password = password ;
    }

    @GeneratedValue
    @Id
    @Column(name= "user_id",nullable = false)
    public long getUserID(){
        return this.userID ;
    }
    public void setUserID(long newID) { this.userID = newID ; }


    @Basic
    @Column(name="email", nullable = false)
    public String getEmail(){
        return this.email ;
    }
    public void setEmail(String email) {
        this.email = email ;
    }

    @Basic
    @Column(name = "password", nullable = false)
    public String getPassword(){
        return this.password ;
    }
    public void setPassword(String password){
        this.password = password ;
    }

    @Override
    public boolean equals(Object o) {
//        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User entity = (User) o;

        if (userID != entity.userID) return false;
        if (email != null ? !email.equals(entity.email) : entity.email != null) return false;
        if (password != null ? !password.equals(entity.password) : entity.password != null) return false;

        return true;
    }
}
