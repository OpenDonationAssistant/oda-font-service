package io.github.opendonationassistant.font.command;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.commons.micronaut.BaseController;
import io.github.opendonationassistant.font.integration.FontsourceApi;
import io.github.opendonationassistant.font.repository.Font;
import io.github.opendonationassistant.font.repository.FontData;
import io.github.opendonationassistant.font.repository.FontRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;

@Controller
public class Refresh extends BaseController {

  private ODALogger log = new ODALogger(this);
  private final FontsourceApi fontsource;
  private final Map<String, List<Font>> cache;
  private final FontRepository repository;

  @Inject
  public Refresh(
    final FontsourceApi fontsource,
    final Map<String, List<Font>> cache,
    final FontRepository repository
  ) {
    this.fontsource = fontsource;
    this.cache = cache;
    this.repository = repository;
  }

  @Post("/fonts/commands/refresh")
  @Secured(SecurityRule.IS_ANONYMOUS)
  @ExecuteOn(TaskExecutors.BLOCKING)
  public void refresh() {
    log.info("Refreshing fonts", Map.of());
    final List<Font> fonts = fontsource
      .fonts()
      .stream()
      .map(font ->
        repository.from(
          new FontData(
            font.id(),
            "fontsource",
            font.family(),
            "",
            Map.of(),
            font.subsets()
          )
        )
      )
      .toList();
    cache.put("fontsource", fonts);
    log.info("Fontsource fonts loaded", Map.of("amount", fonts.size()));
  }
}
