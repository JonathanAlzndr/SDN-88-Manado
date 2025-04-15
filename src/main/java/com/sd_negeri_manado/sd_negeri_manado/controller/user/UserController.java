package com.sd_negeri_manado.sd_negeri_manado.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/")
    public String beranda() {
        return "beranda";
    }

    @GetMapping("/berita")
    public String berita() {
        return "berita";
    }

    @GetMapping("/ekstrakurikuler")
    public String ekstrakurikuler() {
        return "ekstrakurikuler";
    }

    @GetMapping("/galeri")
    public String galeri() {
        return "galeri";
    }

    @GetMapping("/kontak")
    public String kontak() {
        return "kontak";
    }

    @GetMapping("/prestasi")
    public String prestasi() {
        return "prestasi";
    }

    @GetMapping("/profile")
    public String profil() {
        return "profile";
    }

    @GetMapping("/program")
    public String program() {
        return "program";
    }

    @GetMapping("/struktur-organisasi")
    public String strukturOrganisasi() {
        return "struktur-organisasi";
    }

    @GetMapping("/visi-misi")
    public String visiMisi() {
        return "visi-misi";
    }
}
