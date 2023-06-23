package com.brs.bookrentalsystem.model.audit;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        //TODO:: security context holder's name extraction
        return Optional.of("Shashi");
    }
}
