/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    "com.nhom13.services"
})
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //cung cap thong tin chung thuc
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //phan quyen 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password");

        http.formLogin().defaultSuccessUrl("/")
                .failureUrl("/login?error");

        http.logout().logoutSuccessUrl("/login");

        http.exceptionHandling().accessDeniedPage("/login?accessDenied");

        http.authorizeRequests().antMatchers("/").access("hasAuthority('admin')")
                .antMatchers("/admin").access("hasAuthority('admin')")
                .antMatchers("/feedbacks").access("hasAuthority('admin')")
                .antMatchers("/surveys").access("hasAuthority('admin')")
                .antMatchers("/add_survey").access("hasAuthority('admin')")
                .antMatchers("/survey_detail").access("hasAuthority('admin')")
                .antMatchers("/visitor").access("hasAuthority('admin')")
                .antMatchers("/electronic-lockers").access("hasAuthority('admin')")
                .antMatchers("/electronic-lockers/**").access("hasAuthority('admin')")
                .antMatchers("/invoice-residents").access("hasAuthority('admin')")
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll();

        http.csrf().disable();
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "dwdvnztnn",
                        "api_key", "571438538929217",
                        "api_secret", "ZdK569SSxGMgAcDKPwatx2Lores",
                        "secure", true));
        return cloudinary;
    }

}
