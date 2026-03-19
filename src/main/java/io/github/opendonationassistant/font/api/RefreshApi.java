package io.github.opendonationassistant.font.api;

import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;

@Tag(name = "Fonts Commands", description = "Font management commands")
@SecurityRequirement(name = "BearerAuth")
public interface RefreshApi {

  @Operation(
    summary = "Refresh fonts",
    description = "Fetches the latest fonts from the Fontsource API and updates the cache"
  )
  @ApiResponse(responseCode = "200", description = "Fonts refreshed successfully")
  @Post("/fonts/commands/refresh")
  @Secured(SecurityRule.IS_ANONYMOUS)
  void refresh();
}
