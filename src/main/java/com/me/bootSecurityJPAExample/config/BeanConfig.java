package com.me.bootSecurityJPAExample.config;

import com.me.bootSecurityJPAExample.service.TestUtilService;
import com.me.bootSecurityJPAExample.utility.TestUtil;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.annotation.RequestScope;


@Configuration
public class BeanConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public TestUtilService testUtilService(TestUtil testUtil) {
//        return new TestUtilService(testUtil);
//    }

    @RequestScope
//    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    @Bean
    public TestUtil testUtil() {
        return new TestUtil();
    }
}
