package io.github.opendonationassistant.font.view;

import java.util.HashMap;
import java.util.Map;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class FontDto {

  private String name;
  private String type;
  private Map<String,String> sources;

  public FontDto(String name, String type) {
    this(name, type, new HashMap<>());
  }

  public FontDto(String name, String type, Map<String, String> sources){
    this.name = name;
    this.type = type;
    this.sources = sources;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public Map<String, String> getSources(){
    return this.sources;
  }
}
