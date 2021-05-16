package com.team32.ong.security;

import com.team32.ong.security.filter.JwtFilterRequest;
import com.team32.ong.service.impl.UserImplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserImplService userImplService;

    @Autowired
    private JwtFilterRequest jwtFilterRequest;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userImplService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**/authenticate").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/api/v1/activities/**").permitAll()
                .antMatchers("/api/v1/categories/**").permitAll()
                .antMatchers("/api/v1/comment/**").permitAll()
                .antMatchers("/api/v1/member/**").permitAll()
                .antMatchers("/api/v1/news/**").permitAll()
                .antMatchers("/api/v1/organization/**").permitAll()
                .antMatchers("/api/v1/testimonials/**").permitAll()
                .antMatchers("/api/v1/role/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/users").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/users").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
