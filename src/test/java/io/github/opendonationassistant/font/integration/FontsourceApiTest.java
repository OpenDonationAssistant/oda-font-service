package io.github.opendonationassistant.font.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@MicronautTest(environments = "allinone")
public class FontsourceApiTest {

  private final ODALogger log = new ODALogger(this);

  @Inject
  FontsourceApi fontsourceApi;

  @Test
  @Disabled
  public void testGettingFonts() {
    log.info("testGettingFonts", Map.of());
    var fonts = fontsourceApi.fonts();
    log.debug("fonts", Map.of("fonts", fonts));
    assertTrue(fonts.size() > 0);
    var expectedFont = fonts
      .stream()
      .filter(font -> "42dot-sans".equals(font.id()))
      .findFirst();
    assertTrue(expectedFont.isPresent());
    assertEquals(
      new FontsourceApi.Font(
        "42dot-sans",
        "42dot Sans",
        "sans-serif",
        List.of("korean", "latin")
      ),
      expectedFont.get()
    );
  }
}
