package com.sd_negeri_manado.sd_negeri_manado.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/")
    public String beranda() {
        return "/user/beranda";
    }

    @GetMapping("/berita")
    public String berita() {
        return "/user/berita";
    }

    @GetMapping("/ekstrakurikuler")
    public String ekstrakurikuler() {
        return "/user/ekstrakurikuler";
    }

    @GetMapping("/galeri")
    public String galeri() {
        return "/user/galeri";
    }

    @GetMapping("/kontak")
    public String kontak() {
        return "/user/kontak";
    }

    @GetMapping("/prestasi")
    public String prestasi() {
        return "/user/prestasi";
    }

    @GetMapping("/profile")
    public String profil() {
        return "/user/profile";
    }

    @GetMapping("/program")
    public String program() {
        return "/user/program";
    }

    @GetMapping("/struktur-organisasi")
    public String strukturOrganisasi() {
        return "/user/struktur-organisasi";
    }

    @GetMapping("/visi-misi")
    public String visiMisi() {
        return "/user/visi-misi";
    }
}
