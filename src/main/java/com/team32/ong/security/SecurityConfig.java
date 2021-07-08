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

    @Autowired
    private JWTUtil jwtUtil;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userImplService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/**/authenticate").permitAll().antMatchers("/login")
                .permitAll()
                .antMatchers("/", "/v2/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/configuration/**")
                .permitAll()
                // ACTIVITIES
                .antMatchers(HttpMethod.GET, "/api/v1/activities/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/activities/").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/activities").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/activities/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/activities").permitAll()
                // CATEGORIES
                .antMatchers(HttpMethod.GET, "/api/v1/categories/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/categories/{id}").hasRole("ADMIN")
                // COMMENT
                .antMatchers(HttpMethod.GET, "/api/v1/comment/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/comment/news/{id}/comments").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/comment").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/comment/addComment").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/comment/{id}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/comment/{id}").hasAnyRole("USER", "ADMIN")
                // CONTACT
                // .antMatchers(HttpMethod.GET,"/api/v1/contact/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/contact/backoffice/contacts").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/contact").hasAnyRole("USER", "ADMIN")
                // .antMatchers(HttpMethod.PUT,"/api/v1/contact").permitAll()
                // .antMatchers(HttpMethod.DELETE,"/api/v1/contact").permitAll()
                // MEMBER
                .antMatchers(HttpMethod.GET, "/api/v1/members/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/members/").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/members").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/members/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/members/update/{id}").hasRole("ADMIN")
                // NEWS
                .antMatchers(HttpMethod.GET, "/api/v1/news/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/news/new").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/news/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/news/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/news/").permitAll()
                // ORGANIZATION
                .antMatchers(HttpMethod.GET, "/api/v1/organization/public").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/organization/new").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/organization/id{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/organization/delete{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/organization/update{id}").hasRole("ADMIN")
                // TESTIMONIALS
                .antMatchers(HttpMethod.GET, "/api/v1/testimonials").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/testimonials").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/testimonials/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/testimonials/{id}").hasRole("ADMIN")
                // SLIDES
                .antMatchers(HttpMethod.GET, "/api/v1/slides/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/slides/").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/slides").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/slides/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/slides/image/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/slides/{id}").hasRole("ADMIN")
                // USE
                .antMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/users/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/users/admin/update/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/users/update/{id}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/users/delete/{id}").hasAnyRole("USER", "ADMIN")
                // AUTH
                // .antMatchers(HttpMethod.POST,"/api/v1/auth/authenticate").hasRole("USER")
                .antMatchers("/api/v1/storage/**").hasRole("ADMIN").anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().accessDeniedHandler(jwtUtil.accessDeniedHandler());
        http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean("authenticationManager")
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
