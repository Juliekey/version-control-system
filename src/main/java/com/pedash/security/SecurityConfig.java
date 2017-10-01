package com.pedash.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

/**
 * Created by Yuliya Pedash on 05.06.2017.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "SELECT name , password, enabled FROM users WHERE name = ?")
                .authoritiesByUsernameQuery(
                        "SELECT name, role  FROM user_roles WHERE name = ?");
    }

    /**
     * @Override public void configure(WebSecurity web) throws Exception {
     * web.ignoring().antMatchers("/resources/**");
     * }
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/remote/**", "/local/**").access("hasAuthority('USER')")
                //     .antMatchers("/documents").access("hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasAuthority('ADMIN')")
                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error")
                .successHandler(authenticationSuccessHandler)
                .usernameParameter("name").passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf();
    }
}
