package io.github.opendonationassistant.font.repository;

import com.fasterxml.uuid.Generators;

import io.github.opendonationassistant.commons.logging.ODALogger;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

@Singleton
public class FontRepository {

  private final ODALogger log = new ODALogger(this);
  private final Map<String, Map<String, String>> storedFonts;
  private final FontDataRepository dataRepository;
  private final Map<String, List<Font>> fontsCache;

  @Inject
  public FontRepository(
    @Named("stored-fonts") final Map<String, Map<String, String>> storedFonts,
    FontDataRepository dataRepository,
    Map<String, List<Font>> fontsCache
  ) {
    this.storedFonts = storedFonts;
    log.info("Loaded stored fonts", Map.of("amount", storedFonts.size()));
    this.dataRepository = dataRepository;
    this.fontsCache = fontsCache;
  }

  public Font create(
    String recipientId,
    String name,
    Map<String, String> sources,
    List<String> subsets
  ) {
    var font = from(
      new FontData(
        Generators.timeBasedEpochGenerator().generate().toString(),
        recipientId,
        name,
        "",
        sources,
        subsets
      )
    );
    font.save();
    return font;
  }

  public List<Font> list(
    String recipientId,
    @Nullable String subset,
    @Nullable String name
  ) {
    List<Font> fonts = new ArrayList<Font>();
    fonts.addAll(
      storedFonts
        .entrySet()
        .stream()
        .map(entry ->
          new FontData(
            entry.getKey(),
            "ODA",
            entry.getKey(),
            "",
            entry.getValue(),
            List.of("cyrillic", "latin")
          )
        )
        .map(this::from)
        .toList()
    );
    fonts.addAll(fontsCache.getOrDefault("fontsource", List.of()));
    fonts.addAll(
      this.dataRepository.findByRecipientId(recipientId)
        .stream()
        .map(this::from)
        .toList()
    );
    if (subset != null) {
      fonts = fonts
        .stream()
        .filter(font -> font.data().subsets().contains(subset))
        .toList();
    }
    if (name != null) {
      fonts = fonts
        .stream()
        .filter(font -> font.data().name().contains(name))
        .toList();
    }
    return fonts;
  }

  public Font from(FontData data) {
    return new Font(data, dataRepository);
  }
}
