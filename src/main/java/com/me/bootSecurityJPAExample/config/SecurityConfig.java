package com.me.bootSecurityJPAExample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // H2-console security config
        http.authorizeRequests()
                .mvcMatchers("/h2-console/**").permitAll()
            .and()
                .headers().frameOptions().disable()
            .and()
                .csrf().disable();
    }
}
