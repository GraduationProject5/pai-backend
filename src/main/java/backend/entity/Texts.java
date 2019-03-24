package backend.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "texts", schema = "GraduationProject5")
public class Texts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int textId;

    @Basic
    @Column(name = "content", nullable = false)
    private String content;

}