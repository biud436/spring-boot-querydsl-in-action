package com.biud436.rest.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import java.security.SecureRandom;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${password.salt}")
    private String passwordSalt;

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web -> web.ignoring().antMatchers("/swagger-ui/**"));
//    }

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http
//                .authorizeRequests()
//                .anyRequest().authenticated();
//
//        http
//                .formLogin();

//        http.cors().disable()
//                .csrf().disable()
//                .formLogin().disable()
//                .headers().frameOptions().disable();

        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                // /api/users 제외
                .antMatchers("/api/users/**").permitAll();


        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

        Filter authFilter = new JwtAuthenticationFilter(jwtTokenProvider);
        http
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom(passwordSalt.getBytes()));
    }

}
