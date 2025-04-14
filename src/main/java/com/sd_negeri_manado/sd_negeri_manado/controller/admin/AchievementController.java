package com.sd_negeri_manado.sd_negeri_manado.controller.admin;

import com.sd_negeri_manado.sd_negeri_manado.entity.Achievement;
import com.sd_negeri_manado.sd_negeri_manado.entity.News;
import com.sd_negeri_manado.sd_negeri_manado.model.AchievementDto;
import com.sd_negeri_manado.sd_negeri_manado.model.NewsDto;
import com.sd_negeri_manado.sd_negeri_manado.repository.AchievementRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AchievementController {

    @Autowired
    private AchievementRepository achievementRepository;

    @GetMapping("/prestasi")
    public String adminPrestasi(Model model) {
        List<Achievement> listAchievements = achievementRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("listAchievement", listAchievements);
        return "/admin/prestasi/admin-prestasi";
    }

    @GetMapping("/prestasi/tambah")
    public String showTambahPrestasiForm(Model model) {
        AchievementDto achievementDto = new AchievementDto();
        model.addAttribute("achievementDto", achievementDto);
        return "admin/prestasi/tambah-prestasi";
    }

    @PostMapping("/prestasi/tambah")
    public String tambahPrestasi(
            @Valid @ModelAttribute AchievementDto achievementDto,
            BindingResult result
    ) {
        if(achievementDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("achievementDto", "imageFile", "Gambar tidak boleh kosong"));
        }

        if(result.hasErrors()) {
            return "admin/prestasi/tambah-prestasi";
        }

        MultipartFile image = achievementDto.getImageFile();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try(InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }

        Achievement achievement = Achievement.builder()
                .date(createdAt)
                .description(achievementDto.getDescription())
                .imageUrl(storageFileName)
                .build();
        achievementRepository.save(achievement);

        return "redirect:/admin/prestasi";
    }

}
