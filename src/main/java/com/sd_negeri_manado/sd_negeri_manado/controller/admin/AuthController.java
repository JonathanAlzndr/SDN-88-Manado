package com.sd_negeri_manado.sd_negeri_manado.controller.admin;

import com.sd_negeri_manado.sd_negeri_manado.service.UserService;
import com.sd_negeri_manado.sd_negeri_manado.entity.User;
import com.sd_negeri_manado.sd_negeri_manado.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String login() {
        return "admin/auth/login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard/dashboard";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("name") String name) 
    {
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

    @GetMapping("/admin/ganti-password")
    public String showGantiPasswordForm(Model model) {
        return "/admin/auth/ganti-password";
    }

    @PostMapping("/admin/ganti-password")
    public String gantiPassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Principal principal,
                                 Model model)  {
        String username = principal.getName();

        if(!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Konfirmasi password tidak cocok");
            return "/admin/auth/ganti-password";
        }

        boolean success = userService.changePassword(username, oldPassword, newPassword);

        if(success) {
            model.addAttribute("success", "password berhasil diubah");
        } else {
            model.addAttribute("error", "password lama salah");
        }

        return "/admin/auth/ganti-password";
    }
}

