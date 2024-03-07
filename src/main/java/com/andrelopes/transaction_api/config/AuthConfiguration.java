package com.andrelopes.transaction_api.config;

import com.andrelopes.transaction_api.enums.RoleEnum;
import com.andrelopes.transaction_api.filters.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Autowired
    SecurityFilter securityFilter;
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authorizeHttpRequests(req -> {
            req.requestMatchers("/h2-console").permitAll();
            req.requestMatchers("/h2-console/**").permitAll();
            req.requestMatchers("/h2-console/login.do").permitAll();
            req.requestMatchers(HttpMethod.POST,"/auth/login").permitAll();
            req.requestMatchers(HttpMethod.POST,"/users/addUser").hasRole(RoleEnum.ADMIN.toString());
            req.requestMatchers(HttpMethod.GET,"/users/getAllUsers").hasRole(RoleEnum.ADMIN.toString());
            req.anyRequest().authenticated();
        });
        httpSecurity.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        return httpSecurity.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
