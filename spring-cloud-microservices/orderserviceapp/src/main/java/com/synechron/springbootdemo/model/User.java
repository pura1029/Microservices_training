package com.synechron.springbootdemo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Setter
@Getter
@ToString
@Table(name="users")
@EqualsAndHashCode(of="userId")
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name="user_id")
    private long userId;

    private String username;

    private String password;

    private String emailAddress;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = EAGER)
    private Set<Role> roles;
}