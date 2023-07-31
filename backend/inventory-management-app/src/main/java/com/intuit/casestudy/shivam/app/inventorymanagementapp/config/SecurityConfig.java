package com.intuit.casestudy.shivam.app.inventorymanagementapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/api/inventory").permitAll() // Allow access to /api/inventory without authentication
                .antMatchers("/api/inventory/update").permitAll()  // Allow access to /api/inventory without authentication
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }
}