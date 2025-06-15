package io.github.opendonationassistant.font.command;

import io.github.opendonationassistant.font.integration.FontsourceApi;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;

@Controller
public class FontCommandController {

  private FontsourceApi fontsource;
  private Map<String, List<String>> fontsCache;

  @Inject
  public FontCommandController(
    FontsourceApi fontsource,
    Map<String, List<String>> fontsCache
  ) {
    this.fontsource = fontsource;
    this.fontsCache = fontsCache;
  }

  @Post("/fonts/commands/refresh")
  @Secured(SecurityRule.IS_ANONYMOUS)
  @ExecuteOn(TaskExecutors.BLOCKING)
  public void refresh(@Body RefreshCommand command) {
    command.executeOn(fontsource, fontsCache);
  }
}
