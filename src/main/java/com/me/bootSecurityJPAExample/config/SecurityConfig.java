package com.me.bootSecurityJPAExample.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2-console/**", "/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .usernameParameter("loginId")
                .passwordParameter("password")
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .logoutSuccessUrl("/")
                .and()
            .headers()
                .frameOptions().disable()
                .and()
            .csrf()
                .disable();
    }
}
