package io.github.opendonationassistant.font.api;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

@Tag(name = "Fonts", description = "Font management operations")
@SecurityRequirement(name = "BearerAuth")
public interface FontApi {

  @Operation(
    summary = "List fonts",
    description = "Retrieves a list of fonts filtered by optional criteria for the authenticated user"
  )
  @ApiResponse(
    responseCode = "200",
    description = "Fonts retrieved successfully",
    content = @Content(
      mediaType = "application/json",
      array = @ArraySchema(schema = @Schema(implementation = FontApi.FontDto.class))
    )
  )
  @ApiResponse(responseCode = "401", description = "Unauthorized - user not authenticated")
  @Get("/fonts")
  @Secured(SecurityRule.IS_AUTHENTICATED)
  HttpResponse<List<FontDto>> listFonts(
    Authentication auth,
    @Parameter(description = "Unicode subset to filter fonts (e.g., 'latin', 'cyrillic')") @QueryValue("subset") @Nullable String subset,
    @Parameter(description = "Font category to filter by (e.g., 'serif', 'sans-serif')") @QueryValue("category") @Nullable String category,
    @Parameter(description = "Font name to search for (partial match)") @QueryValue("name") @Nullable String name
  );

  @Serdeable
  @Schema(description = "Font information")
  record FontDto(
    @Schema(description = "Font name identifier", example = "Roboto") String name,
    @Schema(description = "Display name of the font", example = "Roboto Regular") String displayName,
    @Schema(description = "Font type", example = "sans-serif") String type,
    @Schema(description = "Map of source identifiers to URLs", example = "{\"regular\": \"https://fonts.gstatic.com/roboto/regular.woff2\"}") Map<String, String> sources
  ) {}
}
