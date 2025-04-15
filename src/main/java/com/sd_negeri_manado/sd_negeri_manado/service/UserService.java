package com.sd_negeri_manado.sd_negeri_manado.service;

import com.sd_negeri_manado.sd_negeri_manado.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.sd_negeri_manado.sd_negeri_manado.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) return false;

        User user = optionalUser.get();
        if(!passwordEncoder.matches(oldPassword, user.getPassword())) return false;

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }
}
