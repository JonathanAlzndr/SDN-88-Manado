package com.sd_negeri_manado.sd_negeri_manado.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class VisiMisiController {

    @GetMapping("/visi-misi")
    public String showVisiMisi() {
        return "admin/visimisi/admin-visi-misi";
    }
}
