package io.github.opendonationassistant.font;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
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
  public Map<String, List<String>> donatersCache(
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
}

