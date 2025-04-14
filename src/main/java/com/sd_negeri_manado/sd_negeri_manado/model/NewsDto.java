package com.sd_negeri_manado.sd_negeri_manado.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {

    private MultipartFile imageFile;

    @NotEmpty(message = "Judul tidak boleh kosong")
    private String title;

    @NotEmpty(message="Deskripsi tidak boleh kosong")
    private String description;
}
