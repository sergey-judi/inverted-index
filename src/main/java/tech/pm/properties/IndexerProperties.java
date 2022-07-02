package tech.pm.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class IndexerProperties {

  @Value("${indexer.threads}")
  private int threads;

  @Value("${indexer.source-directory}")
  private String sourceDirectory;

  @Value("${indexer.stop-words-location}")
  private String stopWordsLocation;

  @Value("${indexer.minimal-token-length}")
  private int minimalTokenLength;

  @Value("${indexer.ignored-delimiters}")
  private String[] ignoredDelimiters;

}
