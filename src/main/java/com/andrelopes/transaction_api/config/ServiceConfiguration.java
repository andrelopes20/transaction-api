package com.andrelopes.transaction_api.config;

import com.andrelopes.transaction_api.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public UserService userService(){
        return new UserService();
    }

}
