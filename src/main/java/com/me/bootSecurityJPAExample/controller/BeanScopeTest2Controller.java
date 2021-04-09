package com.me.bootSecurityJPAExample.controller;

import com.me.bootSecurityJPAExample.service.TestUtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bean-scope-test2")
@RestController
public class BeanScopeTest2Controller {

    private final TestUtilService testUtilService;


    @GetMapping("/test1")
    public String testScope1() {
        testUtilService.doSomething();

        log.debug("controller test");

        return testUtilService.toString() + " ---> " + testUtilService.getData();
    }

    @GetMapping("/test2")
    public String testScope2() {
        testUtilService.doSomething();
        testUtilService.doSomething();

        return testUtilService.toString() + " ---> " + testUtilService.getData();
    }

}
