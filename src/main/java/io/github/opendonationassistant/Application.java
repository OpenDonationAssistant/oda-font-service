package io.github.opendonationassistant;

import io.micronaut.context.ApplicationContextBuilder;
import io.micronaut.context.ApplicationContextConfigurer;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.ContextConfigurer;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;
import jakarta.inject.Named;
import jakarta.inject.Qualifier;

import java.util.HashMap;
import java.util.Map;

@OpenAPIDefinition(info = @Info(title = "oda-font-service"))
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

  @Bean
  @Named("stored-fonts")
  public Map<String, Map<String, String>> storedFonts() {
    return Map.of(
      "chava-regular",
      Map.of("truetype", "https://cdn.oda.digital/fonts/Chava-Regular.ttf"),
      "steelfish-regular",
      Map.of("opentype", "https://cdn.oda.digital/fonts/Steelfish%20Rg.otf"),
      "steelfish-outline",
      Map.of(
        "opentype",
        "https://cdn.oda.digital/fonts/Steelfish%20Outline.otf"
      ),
      "zrnic-regular",
      Map.of("opentype", "https://cdn.oda.digital/fonts/zrnic%20rg.otf"),
      "supermolot",
      Map.of("truetype", "https://cdn.oda.digital/fonts/Supermolot.ttf")
    );
  }
}
