package com.innorun.learningmate;

import com.innorun.learningmate.global.security.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class LearningMateApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningMateApplication.class, args);
    }

}
