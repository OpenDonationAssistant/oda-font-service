package io.github.opendonationassistant;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.font.command.Refresh;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Map;

@Singleton
@Requires(env = "standalone")
public class Startup implements ApplicationEventListener<ServerStartupEvent> {

  private final Refresh refresh;
  private ODALogger log = new ODALogger(this);

  @Inject
  public Startup(final Refresh refresh) {
    this.refresh = refresh;
  }

  @Override
  public void onApplicationEvent(ServerStartupEvent event) {
    try {
      refresh.refresh();
    } catch (Exception e) {
      log.error("Error refreshing fonts", Map.of("error", e.getMessage()));
    }
  }
}
