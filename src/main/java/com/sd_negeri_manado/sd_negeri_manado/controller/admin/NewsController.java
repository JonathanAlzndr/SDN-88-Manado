package com.sd_negeri_manado.sd_negeri_manado.controller.admin;

import com.sd_negeri_manado.sd_negeri_manado.entity.News;
import com.sd_negeri_manado.sd_negeri_manado.model.NewsDto;
import com.sd_negeri_manado.sd_negeri_manado.repository.NewsRepository;
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
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("/berita")
    public String adminBerita(Model model) {
        List<News> listNews = newsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("listNews", listNews);
        return "admin/berita/admin-berita";
    }

    @GetMapping("/berita/tambah")
    public String showTambahBeritaForm(Model model) {
        NewsDto newsDto = new NewsDto();
        model.addAttribute("newsDto", newsDto);
        return "admin/berita/tambah-berita";
    }

    @PostMapping("/berita/tambah")
    public String tambahBerita(
            @Valid @ModelAttribute NewsDto newsDto,
            BindingResult result
    ) {

        if(newsDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("newsDto", "imageFile", "Gambar tidak boleh kosong"));
        }

        if(result.hasErrors()) {
            return "admin/berita/tambah-berita";
        }

        MultipartFile image = newsDto.getImageFile();
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

        News news = News.builder()
                .date(createdAt)
                .title(newsDto.getTitle())
                .description(newsDto.getDescription())
                .imageUrl(storageFileName)
                .build();

        newsRepository.save(news);

        return "redirect:/admin/berita";
    }

    @GetMapping("/berita/edit")
    public String showEditBerita(
            Model model,
            @RequestParam Long id
    ) {

        try {
            News news = newsRepository.findById(id).get();
            model.addAttribute("news", news);

            NewsDto newsDto = NewsDto.builder()
                    .description(news.getDescription())
                    .title(news.getTitle())
                    .build();
            model.addAttribute("newsDto", newsDto);
        } catch(Exception e) {
            System.out.println("Exception " + e.getMessage());
            return "redirect:/admin/berita";
        }

        return "admin/berita/edit-berita";
    }

    @PostMapping("/berita/edit")
    public String editBerita(
            Model model,
            @RequestParam Long id,
            @Valid @ModelAttribute NewsDto newsDto,
            BindingResult result
    ) {

        try{
            News news = newsRepository.findById(id).get();
            model.addAttribute("news", news);

            if(result.hasErrors()) {
                return "admin/berita/edit-berita";
            }

            if(!newsDto.getImageFile().isEmpty()) {
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + news.getImageUrl());

                try {
                    Files.delete(oldImagePath);
                } catch(Exception e) {
                    System.out.println("Exception " + e.getMessage());
                }

                MultipartFile image = newsDto.getImageFile();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }

                news.setImageUrl(storageFileName);
            }

            news.setDescription(newsDto.getDescription());
            news.setTitle(newsDto.getTitle());

            newsRepository.save(news);
        } catch(Exception e) {
            System.out.println("Exception " + e.getMessage());
        }

        return "redirect:/admin/berita";
    }

    @GetMapping("/berita/delete")
    public String hapusBerita(
            @RequestParam Long id
    ) {

        try {
            News news = newsRepository.findById(id).get();


            Path imagePath = Paths.get("public/images/" + news.getImageUrl());
            try {
                Files.delete(imagePath);
            } catch(Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }

            newsRepository.delete(news);
        } catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        return "redirect:/admin/berita";
    }


}
