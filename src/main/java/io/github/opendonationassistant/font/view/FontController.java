package io.github.opendonationassistant.font.view;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller("/fonts")
public class FontController {

  private final Map<String, List<String>> fontsCache;

  private final Map<String, Map<String, String>> storedFonts = Map.of(
    "chava-regular",
    Map.of("truetype", "https://cdn.oda.digital/fonts/Chava-Regular.ttf"),
    "steelfish-regular",
    Map.of("opentype", "https://cdn.oda.digital/fonts/Steelfish%20Rg.otf"),
    "steelfish-outline",
    Map.of("opentype", "https://cdn.oda.digital/fonts/Steelfish%20Outline.otf"),
    "zrnic-regular",
    Map.of("opentype", "https://cdn.oda.digital/fonts/zrnic%20rg.otf"),
    "doom",
    Map.of("truetype", "http://cdn.oda.digital/fonts/Doom2016Text-GOlBq.ttf")
  );

  @Inject
  public FontController(Map<String, List<String>> fontsCache) {
    this.fontsCache = fontsCache;
  }

  @Get
  @Secured(SecurityRule.IS_ANONYMOUS)
  public List<FontDto> listFonts() {
    var fonts = new ArrayList<FontDto>();
    var stored = storedFonts
      .entrySet()
      .stream()
      .map(entry -> new FontDto(entry.getKey(), "stored", entry.getValue()))
      .toList();
    var google = fontsCache
      .getOrDefault("fontsource", List.of())
      .stream()
      .map(font -> new FontDto(font, "google"))
      .toList();
    fonts.addAll(stored);
    fonts.addAll(google);
    return fonts;
  }
}
