package com.sd_negeri_manado.sd_negeri_manado.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewsDtoView {
    private String imageUrl;
    private String title;
    private String description;
    private String formattedDate;
}
