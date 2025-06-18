package io.github.opendonationassistant.font.command;

import com.fasterxml.uuid.Generators;
import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.commons.micronaut.BaseController;
import io.github.opendonationassistant.font.repository.FontRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.serde.annotation.Serdeable;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.inject.Inject;
import java.io.ByteArrayInputStream;
import java.util.Map;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;

@Controller
public class UploadCommand extends BaseController {

  private final ODALogger log = new ODALogger(UploadCommand.class);
  private final MinioClient minio;
  private final FontRepository repository;

  @Inject
  public UploadCommand(MinioClient minio, FontRepository repository) {
    this.minio = minio;
    this.repository = repository;
  }

  @Secured(SecurityRule.IS_AUTHENTICATED)
  @Put(
    value = "/fonts/commands/upload",
    consumes = { MediaType.MULTIPART_FORM_DATA },
    produces = { MediaType.APPLICATION_JSON }
  )
  public HttpResponse<UploadCommandResponse> put(
    CompletedFileUpload file,
    Authentication auth
  ) throws Exception {
    var ownerId = getOwnerId(auth);
    if (ownerId.isEmpty()) {
      return HttpResponse.unauthorized();
    }
    var parser = new TTFParser();
    byte[] data = file.getInputStream().readAllBytes();

    final TrueTypeFont font = parser.parseEmbedded(
      new ByteArrayInputStream(data)
    );
    log.info("Uploading font", Map.of("family", font.getName()));

    var name =
      "%s.ttf".formatted(
          Generators.timeBasedEpochGenerator().generate().toString()
        );
    try (var stream = new ByteArrayInputStream(data)) {
      minio.putObject(
        PutObjectArgs.builder()
          .bucket(ownerId.get())
          .object(name)
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .stream(stream, stream.available(), -1)
          .build()
      );
      log.info(
        "Font uploaded",
        Map.of(
          "recipientId",
          ownerId.get(),
          "path",
          name,
          "family",
          font.getName()
        )
      );
    }

    repository.create(
      font.getName(),
      ownerId.get(),
      "https://api.oda.digital/files/%s".formatted(name)
    );

    return HttpResponse.ok(new UploadCommandResponse(name));
  }

  @Serdeable
  public static record UploadCommandResponse(String path) {}
}
