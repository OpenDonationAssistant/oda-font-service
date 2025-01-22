package io.github.opendonationassistant.font.view;

import java.util.List;
import java.util.Map;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

@Controller("/fonts")
public class FontController {

  private final Map<String, List<String>> fontsCache;

  @Inject
  public FontController(Map<String, List<String>> fontsCache) {
    this.fontsCache = fontsCache;
  }

  @Get
  @Secured(SecurityRule.IS_ANONYMOUS)
  public List<FontDto> listFonts() {
    final List<String> fonts = fontsCache.getOrDefault("fontsource", List.of());
    return fonts.stream()
      .map(font -> new FontDto(font, "google"))
      .toList();
  }

}
