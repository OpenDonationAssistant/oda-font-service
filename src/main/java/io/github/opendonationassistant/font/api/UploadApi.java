package io.github.opendonationassistant.font.api;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Fonts", description = "Font management commands")
@SecurityRequirement(name = "BearerAuth")
public interface UploadApi {

  @Operation(
    summary = "Upload font",
    description = "Uploads a TTF font file and stores it in the user's font collection"
  )
  @ApiResponse(
    responseCode = "200",
    description = "Font uploaded successfully",
    content = @Content(
      mediaType = "application/json",
      schema = @Schema(implementation = FontApi.FontDto.class)
    )
  )
  @ApiResponse(responseCode = "401", description = "Unauthorized - user not authenticated")
  @Put(
    value = "/fonts/commands/upload",
    consumes = { MediaType.MULTIPART_FORM_DATA },
    produces = { MediaType.APPLICATION_JSON }
  )
  @Secured(SecurityRule.IS_AUTHENTICATED)
  HttpResponse<FontApi.FontDto> upload(
    CompletedFileUpload file,
    Authentication auth
  ) throws Exception;
}
