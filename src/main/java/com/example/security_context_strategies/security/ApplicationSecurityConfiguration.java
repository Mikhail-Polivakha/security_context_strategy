package com.example.security_context_strategies.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
    }

    @Autowired
    private DummyAuthenticationFilter dummyAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(dummyAuthenticationFilter, BasicAuthenticationFilter.class);
    }
}