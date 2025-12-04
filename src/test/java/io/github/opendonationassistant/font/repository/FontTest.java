package io.github.opendonationassistant.font.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import io.github.opendonationassistant.font.view.FontController.FontDto;

public class FontTest {

  private FontDataRepository repository = mock(FontDataRepository.class);

  @Test
  public void testSaveNewFont() {
    FontData data = new FontData(
      "new-font-id",
      "test-recipient",
      "New Font",
      "http://example.com/new-font.ttf",
      "category",
      Map.of("truetype", "http://example.com/new-font.ttf"),
      List.of("latin")
    );

    when(repository.findById("new-font-id")).thenReturn(Optional.empty());

    new Font(data, repository).save();

    verify(repository, times(1)).findById("new-font-id");
    verify(repository, times(1)).save(data);
    verify(repository, never()).update(any());
  }

  @Test
  public void testSaveExistingFont() {
    FontData data = new FontData(
      "existing-font-id",
      "test-recipient",
      "Existing Font",
      "http://example.com/existing-font.ttf",
      "category",
      Map.of("truetype", "http://example.com/existing-font.ttf"),
      List.of("latin")
    );

    when(repository.findById("existing-font-id")).thenReturn(Optional.of(data));

    new Font(data, repository).save();

    verify(repository, times(1)).findById("existing-font-id");
    verify(repository, times(1)).update(data);
    verify(repository, never()).save(any());
  }

  @Test
  public void testAsDtoWithOdaRecipient() {
    FontData data = new FontData(
      "oda-font-id",
      "ODA",
      "ODA Font",
      "",
      "category",
      Map.of("truetype", "http://example.com/oda-font.ttf"),
      List.of("latin", "cyrillic")
    );

    FontDto dto = new Font(data, repository).asDto();

    assertEquals("oda-font-id", dto.name());
    assertEquals("ODA Font", dto.displayName());
    assertEquals("stored", dto.type());
    assertEquals(
      Map.of("truetype", "http://example.com/oda-font.ttf"),
      dto.sources()
    );
  }

  @Test
  public void testAsDtoWithFontsourceRecipient() {
    FontData data = new FontData(
      "fontsource-font-id",
      "fontsource",
      "Fontsource Font",
      "",
      "category",
      Map.of("truetype", "http://example.com/fontsource-font.ttf"),
      List.of("latin")
    );

    FontDto dto = new Font(data, repository).asDto();

    assertEquals("fontsource-font-id", dto.name());
    assertEquals("Fontsource Font", dto.displayName());
    assertEquals("google", dto.type());
    assertEquals(
      Map.of("truetype", "http://example.com/fontsource-font.ttf"),
      dto.sources()
    );
  }

  @Test
  public void testAsDtoForPersonalFont() {
    FontData data = new FontData(
      "unknown-font-id",
      "unknown-recipient",
      "Unknown Font",
      "",
      "category",
      Map.of("truetype", "http://example.com/unknown-font.ttf"),
      List.of("latin")
    );

    FontDto dto = new Font(data, repository).asDto();

    assertEquals("unknown-font-id", dto.name());
    assertEquals("Unknown Font", dto.displayName());
    assertEquals("personal", dto.type()); // default case
    assertEquals(
      Map.of("truetype", "http://example.com/unknown-font.ttf"),
      dto.sources()
    );
  }
}
