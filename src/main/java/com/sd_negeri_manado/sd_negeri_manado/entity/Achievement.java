package com.sd_negeri_manado.sd_negeri_manado.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "prestasi")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "keterangan")
    private String description;
}
