package io.github.opendonationassistant.font.integration;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import java.util.Map;

@Client("fontsource")
public interface FontsourceApi {
  @Get("/fontlist?type=google")
  public Map<String, String> list();

  @Get("/v1/fonts")
  public List<Font> fonts();

  @Serdeable
  public static record Font(
    String id,
    String family,
    String category,
    List<String> subsets
  ) {}
}
