package com.sd_negeri_manado.sd_negeri_manado.controller.admin;

import com.sd_negeri_manado.sd_negeri_manado.repository.GaleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class GaleryController {

    @Autowired
    private GaleryRepository galeryRepository;

    @GetMapping("/galeri")
    public String adminGaleri() {
        return "admin-gambar";
    }
}
