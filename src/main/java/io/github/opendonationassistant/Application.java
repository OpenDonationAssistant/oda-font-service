package io.github.opendonationassistant;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.context.ApplicationContextBuilder;
import io.micronaut.context.ApplicationContextConfigurer;
import io.micronaut.context.annotation.ContextConfigurer;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;

@OpenAPIDefinition(
    info = @Info(
        title = "oda-font-service",
        version = "0.1"
    )
)
public class Application {

    @ContextConfigurer
    public static class Configurer implements ApplicationContextConfigurer {
        @Override
        public void configure(@NonNull ApplicationContextBuilder builder) {
            builder.defaultEnvironments("standalone");
        }
    }
    public static void main(String[] args) {
        Micronaut.build(args).banner(false).classes(Application.class).start();
    }
}
