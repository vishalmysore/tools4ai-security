package io.github.vishalmysore.secure;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SecureAgentConfiguration.class)
public @interface EnableAgentSecurity {
}
