package io.github.opendonationassistant.font.integration;

import java.util.Map;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client("fontsource")
public interface FontsourceApi {

  @Get("/fontlist?type=google")
  public Map<String, String> list();
}
