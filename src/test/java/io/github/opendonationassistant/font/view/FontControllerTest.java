package io.github.opendonationassistant.font.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.opendonationassistant.font.repository.Font;
import io.github.opendonationassistant.font.repository.FontRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.security.authentication.Authentication;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(InstancioExtension.class)
public class FontControllerTest {

  private FontRepository repository = mock(FontRepository.class);
  private Authentication authentication = mock(Authentication.class);
  private FontController fontController = new FontController(repository);
  Font mockFont = mock(Font.class);
  FontDto mockDto = mock(FontDto.class);
  String recipientId = "testuser";

  @BeforeEach
  public void setup() {
    when(authentication.getAttributes()).thenReturn(
      Map.of("preferred_username", recipientId)
    );
  }

  @Test
  public void testListFonts(
    @Given String subset,
    @Given String name
  ) {
    when(repository.list(any(), any(), any())).thenReturn(List.of(mockFont));
    when(mockFont.asDto()).thenReturn(mockDto);

    HttpResponse<List<FontDto>> response = fontController.listFonts(
      authentication,
      subset,
      name
    );

    verify(repository).list(recipientId, subset, name);
    assertEquals(200, response.getStatus().getCode());
    assertEquals(Optional.of(List.of(mockDto)), response.getBody());
  }

  @Test
  public void testListFontsWithEmptyFontList() {
    when(repository.list(any(), any(), any())).thenReturn(List.of());

    HttpResponse<List<FontDto>> response = fontController.listFonts(
      authentication,
      null,
      null
    );

    assertEquals(200, response.getStatus().getCode());
    assertEquals(Optional.of(List.of()), response.getBody());
  }

  @Test
  public void testListFontsWithWrongAuthentication() {
    final Authentication wrongAuthentication = mock(Authentication.class);
    when(wrongAuthentication.getAttributes()).thenReturn(Map.of());

    HttpResponse<List<FontDto>> response = fontController.listFonts(
      wrongAuthentication,
      null,
      null
    );

    assertEquals(401, response.getStatus().getCode());
  }

  @Test
  public void testListFontsWithNullAuthentication() {
    HttpResponse<List<FontDto>> response = fontController.listFonts(
      null,
      null,
      null
    );
    assertEquals(401, response.getStatus().getCode());
  }

}
