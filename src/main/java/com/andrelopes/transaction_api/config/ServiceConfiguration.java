package com.andrelopes.transaction_api.config;

import com.andrelopes.transaction_api.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ServiceConfiguration {

    @Bean
    public UserService userService(){
        return new UserService();
    }

}
