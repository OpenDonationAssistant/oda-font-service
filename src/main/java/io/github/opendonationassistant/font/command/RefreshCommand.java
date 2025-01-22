package io.github.opendonationassistant.font.command;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.opendonationassistant.font.integration.FontsourceApi;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class RefreshCommand {

  private Logger log = LoggerFactory.getLogger(RefreshCommand.class);

  public void executeOn(final FontsourceApi fountsource, final Map<String, List<String>> cache) {
    log.info("refreshing fonts");
    cache.clear();
    final Map<String, String> fonts = fountsource.list();
    log.info("fonts amount: {}", fonts.size());
    cache.put("fontsource", fonts.keySet().stream().toList());
    log.info("saved fonts amount: {}", cache.getOrDefault("fontsource", List.of()).size());
  }
}
