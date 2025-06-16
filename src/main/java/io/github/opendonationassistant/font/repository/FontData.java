package io.github.opendonationassistant.font.repository;

import io.micronaut.data.annotation.Id;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record FontData(
  @Id String id,
  String recipientId,
  String name,
  String url
) {}
