package com.me.bootSecurityJPAExample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;


@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.me.bootSecurityJPAExample.repository")
@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return Optional.ofNullable(auth == null ? null : auth.getName());
        };
    }

}
