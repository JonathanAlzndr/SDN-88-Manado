package com.sd_negeri_manado.sd_negeri_manado.controller;

import com.sd_negeri_manado.sd_negeri_manado.entity.User;
import com.sd_negeri_manado.sd_negeri_manado.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;


    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";  // Halaman yang diakses setelah login berhasil
    }

    @PostMapping("/register")
    public String registerUser( @RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String email,
                                @RequestParam String name) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "Username sudah digunakan!";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setName(name);
        user.setPassword(encoder.encode(password));

        userRepository.save(user);
        return "User berhasil didaftarkan!";
    }
}

