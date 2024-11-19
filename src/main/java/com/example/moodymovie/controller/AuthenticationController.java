package com.example.moodymovie.controller;

import com.example.moodymovie.controller.request.AuthenticationRequest;
import com.example.moodymovie.entities.User;
import com.example.moodymovie.repository.UserRepository;
import com.example.moodymovie.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @ModelAttribute("requestUri")
    String getRequestServletPath(final HttpServletRequest request) {
        return request.getRequestURI();
    }
    @GetMapping("/login")
    public String login(@RequestParam(name = "loginRequired", required = false) final Boolean loginRequired,
                        @RequestParam(name = "loginError", required = false) final Boolean loginError,
                        @RequestParam(name = "logoutSuccess", required = false) final Boolean logoutSuccess,
                        final Model model) {
        model.addAttribute("authentication", new AuthenticationRequest());
        if (loginRequired == Boolean.TRUE) {
            model.addAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("authentication.login.required"));
        }
        if (loginError == Boolean.TRUE) {
            model.addAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("authentication.login.error"));
        }
        if (logoutSuccess == Boolean.TRUE) {
            model.addAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("authentication.logout.success"));
        }
        return "login";
    }


    // basic singup
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user) {
        user.setHash(passwordEncoder.encode(user.getHash()));
        userRepository.save(user);
        return "redirect:/login";
    }
}
