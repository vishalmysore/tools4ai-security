package io.github.vishalmysore;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;

public interface AuthChecker {

    public default boolean isMethodInContext(Method m) {
        PreAuthorize preAuthorize = m.getAnnotation(PreAuthorize.class);
        if (preAuthorize == null) {
            return true; // No authorization required
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false; // No authentication present
        }

        String preAuthValue = preAuthorize.value();
        if (preAuthValue.contains("hasRole")) {
            String role = preAuthValue.substring(preAuthValue.indexOf("'") + 1, preAuthValue.lastIndexOf("'"));
            return authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
        }

        return false; // No role requirement found
    }

}
