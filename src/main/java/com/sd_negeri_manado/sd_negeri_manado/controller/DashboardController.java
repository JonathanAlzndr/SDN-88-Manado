package com.sd_negeri_manado.sd_negeri_manado.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

    @GetMapping("/berita")
    public String berita() {
        return "berita";
    }



}
