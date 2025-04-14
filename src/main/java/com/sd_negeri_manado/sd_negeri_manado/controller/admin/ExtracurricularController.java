package com.sd_negeri_manado.sd_negeri_manado.controller.admin;

import com.sd_negeri_manado.sd_negeri_manado.repository.ExtracurricularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExtracurricularController {

    @Autowired
    private ExtracurricularRepository extracurricularRepository;

    @GetMapping("/admin/ekstrakurikuler")
    public String adminEkstrakulikuler() {
        return "admin-ekstrakurikuler";
    }

}
