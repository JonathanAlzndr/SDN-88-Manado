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
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/prestasi/edit")
    public String showEditPrestasi(
            Model model,
            @RequestParam Long id
    ) {

        try {
            Achievement achievement = achievementRepository.findById(id).get();
            model.addAttribute("achievement", achievement);

            AchievementDto achievementDto = AchievementDto.builder()
                    .description(achievement.getDescription())
                    .build();
            model.addAttribute("achievementDto", achievementDto);
        } catch(Exception e) {
            System.out.println("Exception " + e.getMessage());
            return "redirect:/admin/prestasi";
        }

        return "admin/prestasi/edit-prestasi";
    }

    @PostMapping("/prestasi/edit")
    public String editPrestasi(
            Model model,
            @RequestParam Long id,
            @Valid @ModelAttribute AchievementDto achievementDto,
            BindingResult result
    ) {

        try{
            Achievement achievement = achievementRepository.findById(id).get();
            model.addAttribute("achievement", achievement);

            if(result.hasErrors()) {
                return "admin/prestasi/edit-prestasi";
            }

            if(!achievementDto.getImageFile().isEmpty()) {
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + achievement.getImageUrl());

                try {
                    Files.delete(oldImagePath);
                } catch(Exception e) {
                    System.out.println("Exception " + e.getMessage());
                }

                MultipartFile image = achievementDto.getImageFile();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }

                achievement.setImageUrl(storageFileName);
            }

            achievement.setDescription(achievementDto.getDescription());

            achievementRepository.save(achievement);
        } catch(Exception e) {
            System.out.println("Exception " + e.getMessage());
        }

        return "redirect:/admin/prestasi";
    }

    @GetMapping("/prestasi/delete")
    public String hapusBerita(
            @RequestParam Long id
    ) {

        try {
            Achievement achievement = achievementRepository.findById(id).get();

            Path imagePath = Paths.get("public/images/" + achievement.getImageUrl());
            try {
                Files.delete(imagePath);
            } catch(Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }

            achievementRepository.delete(achievement);
        } catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        return "redirect:/admin/prestasi";
    }
}
