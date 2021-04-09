package com.me.bootSecurityJPAExample.service;

import com.me.bootSecurityJPAExample.utility.TestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestUtilService {

    private final TestUtil testUtil;

    public int getData() {
        return testUtil.getData();
    }

    public void doSomething() {
        testUtil.doSomething();
    }
}
