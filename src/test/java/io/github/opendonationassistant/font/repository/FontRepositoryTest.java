package io.github.opendonationassistant.font.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.opendonationassistant.commons.logging.ODALogger;
import java.util.*;
import org.instancio.Instancio;
import org.instancio.Select;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(InstancioExtension.class)
public class FontRepositoryTest {

  private final ODALogger log = new ODALogger(this);

  private Map<String, Map<String, String>> storedFonts = new HashMap<>();
  private Map<String, List<Font>> fontsCache = new HashMap<>();

  private FontDataRepository mockDataRepository = mock(
    FontDataRepository.class
  );

  private FontRepository fontRepository = new FontRepository(
    storedFonts,
    mockDataRepository,
    fontsCache
  );

  @Test
  public void testCreateFont(
    @Given String recipientId,
    @Given String name,
    @Given Map<String, String> sources,
    @Given List<String> subsets
  ) {
    when(mockDataRepository.findById(any())).thenReturn(Optional.empty());
    when(mockDataRepository.save(any())).thenReturn(any());

    Font createdFont = fontRepository.create(
      recipientId,
      name,
      sources,
      subsets
    );

    assertNotNull(createdFont);
    assertNotNull(createdFont.data());
    assertNotNull(createdFont.data().id());

    var expectedData = new FontData(
      createdFont.data().id(),
      recipientId,
      name,
      "",
      sources,
      subsets
    );

    assertEquals(expectedData, createdFont.data());
    verify(mockDataRepository, times(1)).save(expectedData);
  }

  @Test
  public void testListingFonts(
    @Given List<FontData> personalFonts,
    @Given List<Font> fontsourceFonts,
    @Given String fontName,
    @Given String fontType,
    @Given String fontUrl
  ) {
    when(mockDataRepository.findByRecipientId(any())).thenReturn(personalFonts);
    fontsCache.put("fontsource", fontsourceFonts);
    storedFonts.put(fontName, Map.of(fontType, fontUrl));

    var actualFonts = new ArrayList<>(
      new FontRepository(storedFonts, mockDataRepository, fontsCache)
        .list("recipientId", null, null)
        .stream()
        .map(Font::data)
        .toList()
    );

    var expectedFonts = new ArrayList<FontData>();
    expectedFonts.addAll(personalFonts);
    expectedFonts.addAll(fontsourceFonts.stream().map(Font::data).toList());
    expectedFonts.add(
      new FontData(
        fontName,
        "ODA",
        fontName,
        "",
        Map.of(fontType, fontUrl),
        List.of("cyrillic", "latin")
      )
    );
    expectedFonts.sort(Comparator.comparing(FontData::id));
    actualFonts.sort(Comparator.comparing(FontData::id));
    assertEquals(expectedFonts, actualFonts);
  }

  @Test
  public void testListingCyrillicFonts() {
    final List<FontData> cyrillicPersonalFonts = Instancio.of(FontData.class)
      .set(Select.field(FontData::subsets), List.of("cyrillic"))
      .stream()
      .limit(3)
      .toList();
    final List<FontData> nonCyrillicPersonalFonts = Instancio.of(FontData.class)
      .set(Select.field(FontData::subsets), List.of("latin"))
      .stream()
      .limit(20)
      .toList();
    final List<FontData> personalFonts = new ArrayList<>();
    personalFonts.addAll(cyrillicPersonalFonts);
    personalFonts.addAll(nonCyrillicPersonalFonts);
    when(mockDataRepository.findByRecipientId(any())).thenReturn(personalFonts);

    final List<Font> cyrillicFontsourceFonts = Instancio.of(FontData.class)
      .set(Select.field(FontData::subsets), List.of("cyrillic"))
      .stream()
      .limit(3)
      .map(data -> new Font(data, mockDataRepository))
      .toList();
    final List<Font> nonCyrillicFontsourceFonts = Instancio.of(FontData.class)
      .set(Select.field(FontData::subsets), List.of("latin"))
      .stream()
      .limit(20)
      .map(data -> new Font(data, mockDataRepository))
      .toList();
    final List<Font> fontsourceFonts = new ArrayList<>();
    fontsourceFonts.addAll(cyrillicFontsourceFonts);
    fontsourceFonts.addAll(nonCyrillicFontsourceFonts);
    fontsCache.put("fontsource", fontsourceFonts);

    storedFonts.put("font-name", Map.of("font-type", "font-url"));

    final List<FontData> expectedFonts = new ArrayList<>();
    expectedFonts.addAll(cyrillicPersonalFonts);
    expectedFonts.addAll(
      cyrillicFontsourceFonts.stream().map(Font::data).toList()
    );
    expectedFonts.add(
      new FontData(
        "font-name",
        "ODA",
        "font-name",
        "",
        Map.of("font-type", "font-url"),
        List.of("cyrillic", "latin")
      )
    );
    expectedFonts.sort(Comparator.comparing(FontData::id));

    var actualFonts = new ArrayList<>(
      fontRepository
        .list("recipientId", "cyrillic", null)
        .stream()
        .map(Font::data)
        .toList()
    );
    actualFonts.sort(Comparator.comparing(FontData::id));
    assertEquals(expectedFonts, actualFonts);
  }

  @Test
  public void testListingFontsByName() {
    final List<FontData> targetPersonalFonts = Instancio.of(FontData.class)
      .set(Select.field(FontData::name), "test-1")
      .stream()
      .limit(1)
      .toList();
    final List<FontData> nonTargetPersonalFonts = Instancio.of(FontData.class)
      .set(Select.field(FontData::name), "unreachable")
      .stream()
      .limit(20)
      .toList();
    final List<FontData> personalFonts = new ArrayList<>();
    personalFonts.addAll(targetPersonalFonts);
    personalFonts.addAll(nonTargetPersonalFonts);
    when(mockDataRepository.findByRecipientId(any())).thenReturn(personalFonts);

    final List<Font> targetFontsourceFonts = Instancio.of(FontData.class)
      .set(Select.field(FontData::name), "test")
      .stream()
      .limit(3)
      .map(data -> new Font(data, mockDataRepository))
      .toList();
    final List<Font> nonTargetFontsourceFonts = Instancio.of(FontData.class)
      .set(Select.field(FontData::name), "unreachable")
      .stream()
      .limit(20)
      .map(data -> new Font(data, mockDataRepository))
      .toList();
    final List<Font> fontsourceFonts = new ArrayList<>();
    fontsourceFonts.addAll(targetFontsourceFonts);
    fontsourceFonts.addAll(nonTargetFontsourceFonts);
    fontsCache.put("fontsource", fontsourceFonts);

    storedFonts.put("3-test-3", Map.of("font-type", "font-url"));
    storedFonts.put("unreachable-name", Map.of("font-type", "font-url"));

    final List<FontData> expectedFonts = new ArrayList<>();
    expectedFonts.addAll(targetPersonalFonts);
    expectedFonts.addAll(
      targetFontsourceFonts.stream().map(Font::data).toList()
    );
    expectedFonts.add(
      new FontData(
        "3-test-3",
        "ODA",
        "3-test-3",
        "",
        Map.of("font-type", "font-url"),
        List.of("cyrillic", "latin")
      )
    );
    expectedFonts.sort(Comparator.comparing(FontData::id));

    var actualFonts = new ArrayList<>(
      fontRepository
        .list("recipientId", null, "test")
        .stream()
        .map(Font::data)
        .toList()
    );
    actualFonts.sort(Comparator.comparing(FontData::id));
    assertEquals(expectedFonts, actualFonts);
  }

  @Test
  public void testListingBySubsetAndName() {
    final List<FontData> targetPersonalFonts = Instancio.of(FontData.class)
      .set(Select.field(FontData::name), "test-1")
      .set(Select.field(FontData::subsets), List.of("cyrillic"))
      .stream()
      .limit(1)
      .toList();
    final List<FontData> personalFonts = new ArrayList<>();
    personalFonts.addAll(targetPersonalFonts);
    personalFonts.addAll(
      Instancio.of(FontData.class)
        .set(Select.field(FontData::subsets), List.of("cyrillic"))
        .stream()
        .limit(3)
        .toList()
    );
    personalFonts.addAll(
      Instancio.of(FontData.class)
        .set(Select.field(FontData::name), "test-1")
        .stream()
        .limit(1)
        .toList()
    );
    personalFonts.addAll(
      Instancio.of(FontData.class)
        .set(Select.field(FontData::name), "unreachable-1")
        .set(Select.field(FontData::subsets), List.of("latin"))
        .stream()
        .limit(1)
        .toList()
    );
    when(mockDataRepository.findByRecipientId(any())).thenReturn(personalFonts);

    final List<Font> targetFontsourceFonts = Instancio.of(FontData.class)
      .set(Select.field(FontData::name), "test")
      .set(Select.field(FontData::subsets), List.of("cyrillic", "latin"))
      .stream()
      .limit(1)
      .map(data -> new Font(data, mockDataRepository))
      .toList();
    final List<Font> fontsourceFonts = new ArrayList<>();
    fontsourceFonts.addAll(targetFontsourceFonts);
    fontsourceFonts.addAll(
      Instancio.of(FontData.class)
        .set(Select.field(FontData::name), "test")
        .stream()
        .limit(3)
        .map(data -> new Font(data, mockDataRepository))
        .toList()
    );
    fontsourceFonts.addAll(
      Instancio.of(FontData.class)
        .set(Select.field(FontData::subsets), List.of("cyrillic", "latin"))
        .stream()
        .limit(3)
        .map(data -> new Font(data, mockDataRepository))
        .toList()
    );
    fontsourceFonts.addAll(
      Instancio.of(FontData.class)
        .set(Select.field(FontData::name), "unreachable")
        .set(Select.field(FontData::subsets), List.of("latin"))
        .stream()
        .limit(3)
        .map(data -> new Font(data, mockDataRepository))
        .toList()
    );
    fontsCache.put("fontsource", fontsourceFonts);

    storedFonts.put("3-test-3", Map.of("font-type", "font-url"));
    storedFonts.put("unreachable-name", Map.of("font-type", "font-url"));

    final List<FontData> expectedFonts = new ArrayList<>();
    expectedFonts.addAll(targetPersonalFonts);
    expectedFonts.addAll(
      targetFontsourceFonts.stream().map(Font::data).toList()
    );
    expectedFonts.add(
      new FontData(
        "3-test-3",
        "ODA",
        "3-test-3",
        "",
        Map.of("font-type", "font-url"),
        List.of("cyrillic", "latin")
      )
    );
    expectedFonts.sort(Comparator.comparing(FontData::id));

    var actualFonts = new ArrayList<>(
      fontRepository
        .list("recipientId", "cyrillic", "test")
        .stream()
        .map(Font::data)
        .toList()
    );
    actualFonts.sort(Comparator.comparing(FontData::id));
    assertEquals(expectedFonts, actualFonts);
  }
}
