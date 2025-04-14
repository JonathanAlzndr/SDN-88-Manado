package com.sd_negeri_manado.sd_negeri_manado.controller.admin;

import com.sd_negeri_manado.sd_negeri_manado.entity.Galery;
import com.sd_negeri_manado.sd_negeri_manado.entity.News;
import com.sd_negeri_manado.sd_negeri_manado.model.GaleryDto;
import com.sd_negeri_manado.sd_negeri_manado.model.NewsDto;
import com.sd_negeri_manado.sd_negeri_manado.repository.GaleryRepository;
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
public class GaleryController {

    @Autowired
    private GaleryRepository galeryRepository;

    @GetMapping("/galeri")
    public String adminGaleri(Model model) {
        List<Galery> listGalery = galeryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("listGalery", listGalery);
        return "admin/galeri/admin-galeri";
    }

    @GetMapping("/galeri/tambah")
    public String showTambahGaleriForm(Model model) {
        GaleryDto galeryDto = new GaleryDto();
        model.addAttribute("galeryDto", galeryDto);
        return "admin/galeri/tambah-galeri";
    }

    @PostMapping("/galeri/tambah")
    public String tambahGaleri(
            @Valid @ModelAttribute GaleryDto galeryDto,
            BindingResult result
    ) {

        if(galeryDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("galeryDto", "imageFile", "Gambar tidak boleh kosong"));
        }

        if(result.hasErrors()) {
            return "admin/galeri/tambah-galeri";
        }

        MultipartFile image = galeryDto.getImageFile();
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


        Galery galery = Galery.builder()
                .description(galeryDto.getDescription())
                .imageUrl(storageFileName)
                .build();

        galeryRepository.save(galery);

        return "redirect:/admin/galeri";
    }

    @GetMapping("/galeri/edit")
    public String showEditGaleri(
            Model model,
            @RequestParam Long id
    ) {

        try {
            Galery galery = galeryRepository.findById(id).get();
            model.addAttribute("galery", galery);

            GaleryDto galeryDto = GaleryDto.builder()
                    .description(galery.getDescription())
                    .build();
            model.addAttribute("galeryDto", galeryDto);
        } catch(Exception e) {
            System.out.println("Exception " + e.getMessage());
            return "redirect:/admin/galeri";
        }

        return "admin/galeri/edit-galeri";
    }

    @PostMapping("/galeri/edit")
    public String editGaleri(
            Model model,
            @RequestParam Long id,
            @Valid @ModelAttribute GaleryDto galeryDto,
            BindingResult result
    ) {

        try{

            Galery galery = galeryRepository.findById(id).get();
            model.addAttribute("galery", galery);

            if(result.hasErrors()) {
                return "admin/galeri/edit-galeri";
            }

            if(!galeryDto.getImageFile().isEmpty()) {
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + galery.getImageUrl());

                try {
                    Files.delete(oldImagePath);
                } catch(Exception e) {
                    System.out.println("Exception " + e.getMessage());
                }

                MultipartFile image = galeryDto.getImageFile();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }

                galery.setImageUrl(storageFileName);
            }

            galery.setDescription(galeryDto.getDescription());

            galeryRepository.save(galery);
        } catch(Exception e) {
            System.out.println("Exception " + e.getMessage());
        }

        return "redirect:/admin/galeri";
    }

    @GetMapping("/galeri/delete")
    public String hapusGaleri(
            @RequestParam Long id
    ) {

        try {
            Galery galery = galeryRepository.findById(id).get();

            Path imagePath = Paths.get("public/images/" + galery.getImageUrl());
            try {
                Files.delete(imagePath);
            } catch(Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }

            galeryRepository.delete(galery);
        } catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        return "redirect:/admin/galeri";
    }
}
