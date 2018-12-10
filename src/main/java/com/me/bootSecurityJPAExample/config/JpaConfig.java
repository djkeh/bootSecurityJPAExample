package com.me.bootSecurityJPAExample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.me.bootSecurityJPAExample.repository")
@EnableJpaAuditing
@Configuration
public class JpaConfig {

}
