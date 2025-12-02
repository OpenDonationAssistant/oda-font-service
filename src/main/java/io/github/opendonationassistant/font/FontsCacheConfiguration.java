package io.github.opendonationassistant.font;

import io.github.opendonationassistant.font.repository.Font;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.infinispan.client.hotrod.DataFormat;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.commons.marshall.UTF8StringMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Factory
public class FontsCacheConfiguration {

  private Logger log = LoggerFactory.getLogger(FontsCacheConfiguration.class);

  @Bean
  @Singleton
  public Map<String, List<Font>> fontsCache(
    // RemoteCacheManager cacheManager
  ) {
    log.info("creating fonts cache");
    return new HashMap<>();
    // return cacheManager
    //   .getCache(DONATERS_CACHE_NAME)
    //   .withDataFormat(
    //     DataFormat
    //       .builder()
    //       .keyMarshaller(new UTF8StringMarshaller())
    //       .build()
    //   );
  }
  @Bean
  @Named("stored-fonts")
  public Map<String, Map<String, String>> storedFonts() {
    return Map.of(
      "chava-regular",
      Map.of("truetype", "https://cdn.oda.digital/fonts/Chava-Regular.ttf"),
      "steelfish-regular",
      Map.of("opentype", "https://cdn.oda.digital/fonts/Steelfish%20Rg.otf"),
      "steelfish-outline",
      Map.of(
        "opentype",
        "https://cdn.oda.digital/fonts/Steelfish%20Outline.otf"
      ),
      "zrnic-regular",
      Map.of("opentype", "https://cdn.oda.digital/fonts/zrnic%20rg.otf"),
      "supermolot",
      Map.of("truetype", "https://cdn.oda.digital/fonts/Supermolot.ttf")
    );
  }
}

