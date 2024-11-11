package com.dsp.bulletin1.auditor;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dsp.bulletin1.dto.UserDetailsDTO;

public class AuditorAwareImpl implements AuditorAware<Long> {

	@SuppressWarnings("null")
	@Override
	public Optional<Long> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty();
		}

		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetailsDTO) {
			Long userId = ((UserDetailsDTO) principal).geId();
			return Optional.of(userId);
		}
		return Optional.empty();
	}
}