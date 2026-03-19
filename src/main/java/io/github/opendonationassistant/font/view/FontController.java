package io.github.opendonationassistant.font.view;

import io.github.opendonationassistant.commons.micronaut.BaseController;
import io.github.opendonationassistant.font.api.FontApi;
import io.github.opendonationassistant.font.repository.Font;
import io.github.opendonationassistant.font.repository.FontRepository;
import io.github.opendonationassistant.font.repository.FontRepository.Filters;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

@Controller
public class FontController extends BaseController implements FontApi {

  private final FontRepository repository;

  @Inject
  public FontController(FontRepository repository) {
    this.repository = repository;
  }

  @Override
  @ExecuteOn(TaskExecutors.BLOCKING)
  public HttpResponse<List<FontDto>> listFonts(
    Authentication auth,
    @Nullable String subset,
    @Nullable String category,
    @Nullable String name
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
}
