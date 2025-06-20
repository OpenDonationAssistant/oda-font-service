package io.github.opendonationassistant.font.repository;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.serde.annotation.Serdeable;

@MappedEntity("personal")
@Serdeable
public record FontData(
  @Id String id,
  @MappedProperty("recipient_id") String recipientId,
  String name,
  String url
) {}
