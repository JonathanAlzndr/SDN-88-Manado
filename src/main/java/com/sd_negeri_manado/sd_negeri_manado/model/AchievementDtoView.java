package com.sd_negeri_manado.sd_negeri_manado.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AchievementDtoView {
    private String formattedDate;
    private String imageUrl;
    private String description;
}
