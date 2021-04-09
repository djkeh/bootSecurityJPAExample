package com.me.bootSecurityJPAExample.utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestUtil {

    private int innerData = 0;

    public TestUtil() {
        log.debug("instantiated - data: {}", innerData);
    }

    public int getData() {
        log.debug("getData() - {}", innerData);
        return innerData;
    }

    public void doSomething() {
        log.debug("before: {}", innerData);
        innerData++;
        log.debug("after: {}", innerData);
    }
}
