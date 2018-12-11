package com.me.bootSecurityJPAExample.controller;

import com.me.bootSecurityJPAExample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@Controller
public class MainController {

    private final UserService userService;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("users", userService.getList());

        return "index";
    }

}
