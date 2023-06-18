package com.brs.bookrentalsystem.model.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PersistanceConfig {

    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }
}
//@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
//public class PersistanceConfig {
//
//    @Bean
//    public AuditorAware<String> auditorProvider() {
//        return () -> Optional.of("Shashi");
//    }
//}
