package com.sd_negeri_manado.sd_negeri_manado.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "berita")
public class News {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name="title")
    private String title;

    private Date date;

    @Column(name="keterangan")
    private String description;
}
