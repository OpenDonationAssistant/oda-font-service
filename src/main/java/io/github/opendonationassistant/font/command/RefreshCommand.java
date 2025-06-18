package io.github.opendonationassistant.font.command;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.font.integration.FontsourceApi;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Serdeable
public class RefreshCommand {

  private ODALogger log = new ODALogger(this);

  public void executeOn(
    final FontsourceApi fountsource,
    final Map<String, List<String>> cache
  ) {
    log.info("Refreshing fonts", Map.of());
    cache.clear();
    final Map<String, String> fonts = fountsource.list();
    cache.put("fontsource", fonts.keySet().stream().toList());
    log.info("Fontsource fonts loaded", Map.of("amount", fonts.size()));
  }
}
