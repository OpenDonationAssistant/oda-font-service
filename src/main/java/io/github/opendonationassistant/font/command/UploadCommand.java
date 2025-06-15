package io.github.opendonationassistant.font.command;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.annotation.Nullable;
import java.util.Map;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;

@Controller
public class UploadCommand {

  private final ODALogger log = new ODALogger(this);

  @Secured(SecurityRule.IS_AUTHENTICATED)
  @Put(
    value = "/fonts/commands/upload",
    consumes = { MediaType.MULTIPART_FORM_DATA },
    produces = { MediaType.TEXT_PLAIN }
  )
  public void put(CompletedFileUpload file, Authentication auth)
    throws Exception {
    var parser = new TTFParser();
    final TrueTypeFont font = parser.parseEmbedded(file.getInputStream());
    font.getName();
    log.info("Uploading font", Map.of("name", font.getName()));
  }
}
