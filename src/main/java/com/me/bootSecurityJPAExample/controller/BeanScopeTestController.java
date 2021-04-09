package com.me.bootSecurityJPAExample.controller;

import com.me.bootSecurityJPAExample.service.TestUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/bean-scope-test")
@RestController
public class BeanScopeTestController {

    private final TestUtilService testUtilService;


    @GetMapping("/test1")
    public String testScope1() {
        testUtilService.doSomething();

        return testUtilService.toString() + " ---> " + testUtilService.getData();
    }

    @GetMapping("/test2")
    public String testScope2() {
        testUtilService.doSomething();
        testUtilService.doSomething();
        testUtilService.doSomething();

        return testUtilService.toString() + " ---> " + testUtilService.getData();
    }

}
