package io.github.opendonationassistant.font.repository;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class FontRepository {

  private final FontDataRepository dataRepository;

  @Inject
  public FontRepository(FontDataRepository dataRepository) {
    this.dataRepository = dataRepository;
  }

}
