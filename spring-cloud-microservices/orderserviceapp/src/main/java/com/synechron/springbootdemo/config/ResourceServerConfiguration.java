package com.synechron.springbootdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import static org.springframework.http.HttpMethod.GET;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/webjars/**", "/swagger-resources/**", "/swagger", "/swagger-ui.html",
                        "/swagger-resources", "/csrf")
                .permitAll()
                .antMatchers(GET, "/api/v1/orders/**")
                .hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/v1/orders/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/orders/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/orders/**")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated();
    }

}