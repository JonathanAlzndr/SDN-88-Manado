package com.sd_negeri_manado.sd_negeri_manado.repository;

import com.sd_negeri_manado.sd_negeri_manado.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

interface NewsRepository extends JpaRepository<News, Long> {
}
