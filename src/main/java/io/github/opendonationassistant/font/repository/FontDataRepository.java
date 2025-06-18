package io.github.opendonationassistant.font.repository;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface FontDataRepository extends CrudRepository<FontData, String> {
  List<FontData> findByRecipientId(String recipientId);
}
