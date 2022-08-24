package com.synechron.springbootdemo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name="role_id")
    private int roleId;

    @Column(name="role_name")
    private String roleName;


    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name="role_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private Set<User> users;
}