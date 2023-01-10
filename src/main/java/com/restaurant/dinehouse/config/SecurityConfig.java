package com.restaurant.dinehouse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired(required = false)
    private CustomFilter customFilter;

    @Bean
    @ConditionalOnProperty(value = "system.security.authorization.enable",
            havingValue = "true",
            matchIfMissing = true)
    public FilterRegistrationBean<CustomFilter> authFilter() {
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(customFilter);
        registrationBean.addUrlPatterns("*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable().
                authorizeRequests().
                antMatchers("*").permitAll()
                .and()
                .httpBasic()
                .disable().build();
    }
}
