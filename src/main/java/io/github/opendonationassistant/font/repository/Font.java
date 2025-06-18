package io.github.opendonationassistant.font.repository;

import io.github.opendonationassistant.font.view.FontDto;
import io.micronaut.core.annotation.NonNull;
import jakarta.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;

public class Font {

  private final FontData data;
  private final FontDataRepository repository;

  public Font(@Nonnull FontData data, @Nonnull FontDataRepository repository) {
    this.data = data;
    this.repository = repository;
  }

  public void save() {
    @NonNull
    final Optional<FontData> existing = repository.findById(this.data.id());
    if (existing.isPresent()) {
      this.repository.update(this.data);
    } else {
      this.repository.save(this.data);
    }
  }

  public FontDto asDto() {
    return new FontDto(
      this.data.name(),
      "stored",
      Map.of("truetype", this.data.url())
    );
  }
}
