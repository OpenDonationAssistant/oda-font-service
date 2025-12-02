package io.github.opendonationassistant.font.view;

import io.micronaut.serde.annotation.Serdeable;
import java.util.Map;

@Serdeable
public record FontDto(String name, String type, Map<String, String> sources) {}
