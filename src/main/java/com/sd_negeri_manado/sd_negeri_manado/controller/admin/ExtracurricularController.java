package com.sd_negeri_manado.sd_negeri_manado.controller.admin;

import com.sd_negeri_manado.sd_negeri_manado.entity.Achievement;
import com.sd_negeri_manado.sd_negeri_manado.entity.Extracurricular;
import com.sd_negeri_manado.sd_negeri_manado.entity.News;
import com.sd_negeri_manado.sd_negeri_manado.model.AchievementDto;
import com.sd_negeri_manado.sd_negeri_manado.model.ExtracurricularDto;
import com.sd_negeri_manado.sd_negeri_manado.repository.ExtracurricularRepository;
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
public class ExtracurricularController {

    @Autowired
    private ExtracurricularRepository extracurricularRepository;

    @GetMapping("/ekstrakurikuler")
    public String adminEkstrakurikuler(Model model) {
        List<Extracurricular> listExtracurricular = extracurricularRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("listExtracurricular", listExtracurricular);
        return "admin/ekstrakurikuler/admin-ekstrakurikuler";
    }

    @GetMapping("/ekstrakurikuler/tambah")
    public String showTambahEkstrakurikulerForm(Model model) {
        ExtracurricularDto extracurricularDto = new ExtracurricularDto();
        model.addAttribute("extracurricularDto", extracurricularDto);
        return "admin/ekstrakurikuler/tambah-ekstrakurikuler";
    }

    @PostMapping("/ekstrakurikuler/tambah")
    public String tambahPrestasi(
            @Valid @ModelAttribute ExtracurricularDto extracurricularDto,
            BindingResult result
    ) {
        if(extracurricularDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("extracurricularDto", "imageFile", "Gambar tidak boleh kosong"));
        }

        if(result.hasErrors()) {
            return "admin/ekstrakurikuler/tambah-ekstrakurikuler";
        }

        MultipartFile image = extracurricularDto.getImageFile();
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

        Extracurricular extracurricular = Extracurricular.builder()
                .stakeHolder(extracurricularDto.getStakeHolder())
                .description(extracurricularDto.getDescription())
                .imageUrl(storageFileName)
                .build();

        extracurricularRepository.save(extracurricular);

        return "redirect:/admin/ekstrakurikuler";
    }

    @GetMapping("/ekstrakurikuler/edit")
    public String showEditExtracurricular(
            Model model,
            @RequestParam Long id
    ) {

        try {
            Extracurricular extracurricular = extracurricularRepository.findById(id).get();
            model.addAttribute("extracurricular", extracurricular);
            ExtracurricularDto extracurricularDto = ExtracurricularDto.builder()
                    .description(extracurricular.getDescription())
                    .stakeHolder(extracurricular.getStakeHolder())
                    .build();
            model.addAttribute("extracurricularDto", extracurricularDto);
        } catch(Exception e) {
            System.out.println("Exception " + e.getMessage());
            return "redirect:/admin/ekstrakurikuler";
        }

        return "admin/ekstrakurikuler/edit-ekstrakurikuler";
    }

    @PostMapping("/ekstrakurikuler/edit")
    public String editEkstrakurikuler(
            Model model,
            @RequestParam Long id,
            @Valid @ModelAttribute ExtracurricularDto extracurricularDto,
            BindingResult result
    ) {

        try{
            Extracurricular extracurricular = extracurricularRepository.findById(id).get();
            model.addAttribute("extracurricular", extracurricular);

            if(result.hasErrors()) {
                return "admin/ekstrakurikuler/edit-ekstrakurikuler";
            }

            if(!extracurricularDto.getImageFile().isEmpty()) {
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + extracurricular.getImageUrl());

                try {
                    Files.delete(oldImagePath);
                } catch(Exception e) {
                    System.out.println("Exception " + e.getMessage());
                }

                MultipartFile image = extracurricularDto.getImageFile();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }

                extracurricular.setImageUrl(storageFileName);
            }

            extracurricular.setDescription(extracurricularDto.getDescription());
            extracurricular.setStakeHolder(extracurricularDto.getStakeHolder());
            extracurricularRepository.save(extracurricular);
        } catch(Exception e) {
            System.out.println("Exception " + e.getMessage());
        }

        return "redirect:/admin/ekstrakurikuler";
    }

    @GetMapping("/ekstrakurikuler/delete")
    public String hapusEkstrakurikuler(
            @RequestParam Long id
    ) {

        try {
            Extracurricular extracurricular = extracurricularRepository.findById(id).get();
            Path imagePath = Paths.get("public/images/" + extracurricular.getImageUrl());
            try {
                Files.delete(imagePath);
            } catch(Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }

            extracurricularRepository.delete(extracurricular);
        } catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        return "redirect:/admin/ekstrakurikuler";
    }

}
