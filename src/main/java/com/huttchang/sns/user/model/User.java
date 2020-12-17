package com.huttchang.sns.user.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "users")
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PWD")
    private String pwd;
    @Column(name = "STATUS")
    private int statuss;
    @Column(name = "NICKNAME")
    private String nickname;

}
