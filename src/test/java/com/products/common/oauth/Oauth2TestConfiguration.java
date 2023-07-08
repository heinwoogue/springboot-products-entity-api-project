package com.products.common.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class Oauth2TestConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) {
        // No Implementation
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.stateless(false);
    }
}
