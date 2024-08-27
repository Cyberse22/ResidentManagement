/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.configs;

import com.nhom13.filters.CustomAccessDeniedHandler;
import com.nhom13.filters.JwtAuthenticationTokenFilter;
import com.nhom13.filters.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author ADMIN
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.nhom13.controllers",
    "com.nhom13.repositories",
    "com.nhom13.services", 
    "com.nhom13.components"
})
@Order(1)  //set order de uu tien hon thang spring security
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
        return jwtAuthenticationTokenFilter;
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/api/**");
        http.authorizeRequests().antMatchers("/api/login/").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/resident/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/feedbacks/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/surveys/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/visitor/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/item/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/surveys/").permitAll();
        
        http.antMatcher("/api/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").access("hasAuthority('admin') or hasAuthority('resident')")
                .antMatchers(HttpMethod.POST, "/api/**").access("hasAuthority('admin') or hasAuthority('resident')")
                .antMatchers(HttpMethod.DELETE, "/api/**").access("hasAuthority('admin') or hasAuthority('resident')").and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
    }
    
    
}
