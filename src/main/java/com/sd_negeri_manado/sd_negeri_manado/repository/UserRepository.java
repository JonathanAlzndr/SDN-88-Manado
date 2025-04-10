package com.sd_negeri_manado.sd_negeri_manado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sd_negeri_manado.sd_negeri_manado.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
