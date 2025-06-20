package io.github.opendonationassistant.font.repository;

import com.fasterxml.uuid.Generators;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;

@Singleton
public class FontRepository {

  private final FontDataRepository dataRepository;

  @Inject
  public FontRepository(FontDataRepository dataRepository) {
    this.dataRepository = dataRepository;
  }

  public Font create(String recipientId, String name, String url) {
    var font = new Font(
      new FontData(
        Generators.timeBasedEpochGenerator().generate().toString(),
        recipientId,
        name,
        url
      ),
      dataRepository
    );
    font.save();
    return font;
  }

  public List<Font> list(String recipientId) {
    return this.dataRepository.findByRecipientId(recipientId)
      .stream()
      .map(data -> new Font(data, dataRepository))
      .toList();
  }
}
