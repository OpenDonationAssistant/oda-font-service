package io.github.opendonationassistant.font.repository;

import io.github.opendonationassistant.font.view.FontController.FontDto;
import io.micronaut.core.annotation.NonNull;
import jakarta.annotation.Nonnull;
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

  public FontData data() {
    return this.data;
  }

  public FontDto asDto() {
    return new FontDto(
      this.data.id(),
      this.data.name(),
      switch (this.data.recipientId()) {
        case "ODA" -> "stored";
        case "fontsource" -> "google";
        default -> "personal";
      },
      this.data.sources()
    );
  }
}
