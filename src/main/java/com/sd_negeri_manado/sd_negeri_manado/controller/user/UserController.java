package com.sd_negeri_manado.sd_negeri_manado.controller.user;

import com.sd_negeri_manado.sd_negeri_manado.entity.Achievement;
import com.sd_negeri_manado.sd_negeri_manado.entity.Extracurricular;
import com.sd_negeri_manado.sd_negeri_manado.entity.Galery;
import com.sd_negeri_manado.sd_negeri_manado.entity.News;
import com.sd_negeri_manado.sd_negeri_manado.model.AchievementDtoView;
import com.sd_negeri_manado.sd_negeri_manado.repository.AchievementRepository;
import com.sd_negeri_manado.sd_negeri_manado.repository.ExtracurricularRepository;
import com.sd_negeri_manado.sd_negeri_manado.repository.GaleryRepository;
import com.sd_negeri_manado.sd_negeri_manado.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class UserController {


    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ExtracurricularRepository extracurricularRepository;

    @Autowired
    private GaleryRepository galeryRepository;

    @GetMapping("/")
    public String home() {
        return "/user/beranda";
    }

    @GetMapping("/beranda")

    public String beranda(){
        return "/user/beranda";
    }

    @GetMapping("/berita")
    public String berita(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<News> beritaList;
        if (keyword != null && !keyword.isEmpty()) {
            beritaList = newsRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
        } else {
            beritaList = newsRepository.findAll();
        }

        model.addAttribute("beritaList", beritaList);
        model.addAttribute("keyword", keyword); // supaya input tetap ada nilainya
        return "/user/berita";
    }


    @GetMapping("/ekstrakurikuler")
    public String ekstrakurikuler(Model model) {
        List<Extracurricular> list = extracurricularRepository.findAll();
        model.addAttribute("ekskulList", list);
        return "/user/ekstrakurikuler";
    }

    @GetMapping("/galeri")
    public String galeri(Model model) {
        List<Galery> galeriList = galeryRepository.findAll();
        model.addAttribute("galeriList", galeriList);
        return "/user/galeri";
    }

    @GetMapping("/kontak")
    public String kontak() {
        return "/user/kontak";
    }

    @GetMapping("/prestasi")
    public String prestasi(Model model) {
        List<Achievement> list = achievementRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        Locale locale = new Locale("id", "ID");
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy", locale);

        List<AchievementDtoView> prestasiList = new ArrayList<>();
        for(Achievement item : list) {
            AchievementDtoView dto = AchievementDtoView.builder()
                    .description(item.getDescription())
                    .formattedDate(sdf.format(item.getDate()))
                    .imageUrl(item.getImageUrl())
                    .build();
            prestasiList.add(dto);
        }
        model.addAttribute("prestasiList", prestasiList);
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
