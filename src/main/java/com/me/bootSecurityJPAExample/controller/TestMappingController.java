package com.me.bootSecurityJPAExample.controller;


import com.me.bootSecurityJPAExample.domain.APIResponse;
import com.me.bootSecurityJPAExample.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/test")
@RestController
public class TestMappingController {

//    @GetMapping("/")
    public String testRoot() {
        return "root";
    }

    @GetMapping("")
    public String testRoot2() {
        return "root2";
    }

    @GetMapping("/1")
    public String test1() {
        return "/1";
    }

    @GetMapping("/2")
    public String test2() {
        return "2";
    }

    @GetMapping("/test")
    public ResponseEntity<APIResponse<User>> test() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().nickname("hi").build());
        users.add(User.builder().nickname("there").build());

        return ResponseEntity.ok(APIResponse.success(users, 1));
    }

}
