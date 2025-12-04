package io.github.opendonationassistant.font.view;

import io.github.opendonationassistant.commons.micronaut.BaseController;
import io.github.opendonationassistant.font.repository.Font;
import io.github.opendonationassistant.font.repository.FontRepository;
import io.github.opendonationassistant.font.repository.FontRepository.Filters;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

@Controller("/fonts")
public class FontController extends BaseController {

  private final FontRepository repository;

  @Inject
  public FontController(FontRepository repository) {
    this.repository = repository;
  }

  @Get
  @Secured(SecurityRule.IS_AUTHENTICATED)
  @ExecuteOn(TaskExecutors.BLOCKING)
  public HttpResponse<List<FontDto>> listFonts(
    Authentication auth,
    @QueryValue("subset") @Nullable String subset,
    @QueryValue("category") @Nullable String category,
    @QueryValue("name") @Nullable String name
  ) {
    final Optional<String> ownerId = getOwnerId(auth);
    if (ownerId.isEmpty()) {
      return HttpResponse.unauthorized();
    }
    return HttpResponse.ok(
      repository
        .list(ownerId.get(), new Filters(subset, category, name))
        .stream()
        .map(Font::asDto)
        .toList()
    );
  }

  @Serdeable
  public static record FontDto(
    String name,
    String displayName,
    String type,
    Map<String, String> sources
  ) {}
}
