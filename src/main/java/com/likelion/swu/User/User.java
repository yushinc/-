package com.likelion.swu.User;
import javax.persistence.Entity;

@Entity
public class User {
=======
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="user")
@Getter
@Setter
@ToString
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private long student_number;
    private String name;
    private String password;
    private String major;
    @Enumerated(EnumType.STRING)
    private Role role;

}
