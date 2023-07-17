package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.AuthorityService;
import ru.job4j.accidents.service.UserService;

@Controller
@AllArgsConstructor
public class RegControl {

    private final PasswordEncoder encoder;
    private final UserService users;
    private final AuthorityService authorities;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        if (users.findByEmailAndPassword(user.getUsername(), user.getPassword()).isEmpty()) {
            user.setEnabled(true);
            user.setPassword(encoder.encode(user.getPassword()));
            user.setAuthority(authorities.findByAuthority("ROLE_USER"));
            users.save(user);
            return "redirect:/login";
        }
        model.addAttribute("message", "Пользователь с таким именем существует");
        return "404";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }
}
