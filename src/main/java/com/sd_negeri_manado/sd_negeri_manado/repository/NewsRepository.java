package com.sd_negeri_manado.sd_negeri_manado.repository;

import com.sd_negeri_manado.sd_negeri_manado.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
}
