package io.github.opendonationassistant.font.repository;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.model.DataType;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;
import java.util.Map;

@MappedEntity("personal")
@Serdeable
public record FontData(
  @Id String id,
  @MappedProperty("recipient_id") String recipientId,
  String name,
  String url,
  String category,
  @MappedProperty(value = "sources", type = DataType.JSON)
  Map<String, String> sources,
  @MappedProperty(value = "subsets", type = DataType.JSON)
  List<String> subsets
) {}
