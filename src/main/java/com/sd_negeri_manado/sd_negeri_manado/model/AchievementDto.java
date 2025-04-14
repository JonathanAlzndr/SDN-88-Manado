package com.sd_negeri_manado.sd_negeri_manado.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AchievementDto {

    private MultipartFile imageFile;

    @NotEmpty(message="Deskripsi tidak boleh kosong")
    private String description;
}
