package com.sd_negeri_manado.sd_negeri_manado.entity;

import jakarta.persistence.*;

@Entity
@Table(name="penggguna")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama")
    private String name;

    private String username;

    private String password;

    private String email;

}
