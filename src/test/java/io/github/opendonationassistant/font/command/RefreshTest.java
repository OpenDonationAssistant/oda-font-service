package io.github.opendonationassistant.font.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.opendonationassistant.font.integration.FontsourceApi;
import io.github.opendonationassistant.font.repository.Font;
import io.github.opendonationassistant.font.repository.FontRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class RefreshTest {

  private FontsourceApi fontsourceApi = mock(FontsourceApi.class);
  private FontRepository repository = mock(FontRepository.class);
  private Map<String, List<Font>> cache = new HashMap<>();

  private Refresh refreshController = new Refresh(
    fontsourceApi,
    cache,
    repository
  );

  FontsourceApi.Font apiFont = new FontsourceApi.Font(
    "roboto",
    "Roboto",
    "sans-serif",
    List.of("latin")
  );

  Font domainFont = mock(Font.class);

  @Test
  public void testRefreshFontsFromFontsourceApi() {
    when(fontsourceApi.fonts()).thenReturn(List.of(apiFont));
    when(repository.from(any())).thenReturn(domainFont);

    refreshController.refresh();

    var font = Optional.ofNullable(cache.get("fontsource")).map(List::getLast);
    assertEquals(domainFont, font.get());
  }
}
