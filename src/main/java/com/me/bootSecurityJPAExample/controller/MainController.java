package com.me.bootSecurityJPAExample.controller;

import com.me.bootSecurityJPAExample.domain.User;
import com.me.bootSecurityJPAExample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@RequestMapping("/")
@Controller
public class MainController {

    private final UserService userService;

    @GetMapping
    public String main(Authentication authentication, Model model) {
        if (authentication == null) { return "redirect:/login"; }

        model.addAttribute("users", userService.getList());
        model.addAttribute("myAccount", userService.getUser(authentication.getName()));

        return "index";
    }

    @PostMapping
    public String formMain(Authentication authentication, Model model, User user) {
        if (authentication == null) { return "redirect:/login"; }

        if (!userService.hasUser(user.getLoginId())) {
            userService.addUser(user);
        }

        model.addAttribute("users", userService.getList());
        model.addAttribute("myAccount", userService.getUser(authentication.getName()));

        return "index";
    }

}
