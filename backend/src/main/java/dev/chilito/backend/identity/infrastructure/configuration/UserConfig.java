package dev.chilito.backend.identity.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.chilito.backend.identity.application.usecases.UserService;
import dev.chilito.backend.identity.application.output.IUserOutPort;

@Configuration
public class UserConfig {

    @Bean
    public UserService createUserServiceBean(IUserOutPort userOutPort) {
        return new UserService(userOutPort);
    }

}
