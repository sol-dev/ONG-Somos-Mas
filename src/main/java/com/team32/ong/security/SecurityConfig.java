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
                //ACTIVITIES
                .antMatchers(HttpMethod.GET,"/api/v1/activities/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/activities/").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/activities").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/activities/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/activities").permitAll()
                //CATEGORIES
                .antMatchers(HttpMethod.GET,"/api/v1/categories/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/v1/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/categories/{id}").hasRole("ADMIN")
                //COMMENT
                .antMatchers(HttpMethod.GET,"/api/v1/comment/id").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/comment/posts/id/comments").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/api/v1/comment").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/v1/comment/addComment").hasRole("USER")
                .antMatchers(HttpMethod.PUT,"/api/v1/comment/{id}").hasRole("USER")
                .antMatchers(HttpMethod.DELETE,"/api/v1/comment/{id}").hasRole("USER")
                //CONTACT
                .antMatchers(HttpMethod.GET,"/api/v1/contacts/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/contacts/").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/v1/contacts").hasRole("USER")
                .antMatchers(HttpMethod.PUT,"/api/v1/contacts").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/v1/contacts").permitAll()
                //MEMBER
                .antMatchers(HttpMethod.GET,"/api/v1/member/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/member").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/v1/member").hasRole("USER")
                .antMatchers(HttpMethod.PUT,"/api/v1/member/{id}").hasRole("USER")
                .antMatchers(HttpMethod.DELETE,"/api/v1/member").hasRole("USER")
                //NEWS
                .antMatchers(HttpMethod.GET,"/api/v1/news/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/v1/news/new").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/news/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/news/{id}").hasRole("ADMIN")
                //ORGANIZATION
                .antMatchers(HttpMethod.GET,"/api/v1/organization/{id}").hasRole("USER")
                .antMatchers(HttpMethod.POST,"/api/v1/organization").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/v1/organization").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/organization").permitAll()
                //TESTIMONIALS
                .antMatchers(HttpMethod.GET,"/api/v1/testimonials").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/testimonials").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/testimonials/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/testimonials/{id}").hasRole("ADMIN")
                //SLIDES
                .antMatchers(HttpMethod.GET,"/api/v1/slides/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/slides/").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/v1/slides").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/slides/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/slides/{id}").hasRole("ADMIN")
                //USER
                .antMatchers(HttpMethod.GET,"/api/v1/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/users/{id}").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/users").hasRole("USER")
                .antMatchers(HttpMethod.PUT,"/api/v1/users/admin/update/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/users/update/{id}").hasRole("USER")
                .antMatchers(HttpMethod.DELETE,"/api/v1/users/delete/{id}").hasRole("USER")
                //AUTH
                //.antMatchers(HttpMethod.POST,"/api/v1/auth/authenticate").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().accessDeniedHandler(jwtUtil.accessDeniedHandler());
        http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
