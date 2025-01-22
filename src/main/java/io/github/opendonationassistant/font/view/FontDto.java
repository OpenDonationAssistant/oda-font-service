package io.github.opendonationassistant.font.view;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class FontDto {

  private String name;
  private String type;

  public FontDto(String name, String type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }
}
