package com.sd_negeri_manado.sd_negeri_manado.entity;

import jakarta.persistence.*;

@Entity
@Table(name="ekstrakurikuler")
public class Extracurricular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name="keterangan")
    private String description;
}
