package com.brs.bookrentalsystem.model.audit;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {


    @Override
    public @NotNull Optional<String> getCurrentAuditor() {
        //TODO:: security context holder's name extraction

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String defaultAuditor = "System";

        if (Objects.nonNull(authentication)) {
            defaultAuditor = authentication.getName();
            return Optional.of(defaultAuditor);
        }
        return Optional.of(defaultAuditor);
    }
}
