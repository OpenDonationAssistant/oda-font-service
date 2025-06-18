package io.github.opendonationassistant.font.view;

import io.github.opendonationassistant.commons.micronaut.BaseController;
import io.github.opendonationassistant.font.repository.FontRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller("/fonts")
public class FontController extends BaseController {

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
    Map.of("truetype", "https://cdn.oda.digital/fonts/Doom2016Text-GOlBq.ttf"),
    "supermolot",
    Map.of("truetype", "https://cdn.oda.digital/fonts/Supermolot.ttf")
  );

  private final FontRepository repository;

  @Inject
  public FontController(
    Map<String, List<String>> fontsCache,
    FontRepository repository
  ) {
    this.fontsCache = fontsCache;
    this.repository = repository;
  }

  @Get
  @Secured(SecurityRule.IS_AUTHENTICATED)
  public HttpResponse<List<FontDto>> listFonts(Authentication auth) {
    final Optional<String> ownerId = getOwnerId(auth);
    if (ownerId.isEmpty()) {
      return HttpResponse.unauthorized();
    }
    var fonts = new ArrayList<FontDto>();
    fonts.addAll(
      storedFonts
        .entrySet()
        .stream()
        .map(entry -> new FontDto(entry.getKey(), "stored", entry.getValue()))
        .toList()
    );
    fonts.addAll(
      fontsCache
        .getOrDefault("fontsource", List.of())
        .stream()
        .map(font -> new FontDto(font, "google"))
        .toList()
    );
    fonts.addAll(
      repository.list(ownerId.get()).stream().map(font -> font.asDto()).toList()
    );
    return HttpResponse.ok(fonts);
  }
}
