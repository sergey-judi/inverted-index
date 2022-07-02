package tech.pm.domain;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import tech.pm.model.Document;
import tech.pm.model.TokenDetails;
import tech.pm.properties.IndexerProperties;
import tech.pm.util.FileReaderUtils;
import tech.pm.util.IndexerUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Indexer {

  private final IndexerProperties indexerProperties;

  private final Set<String> stopWords;
  private final ConcurrentHashMap<String, TokenDetails> invertedIndex;

  public Indexer(IndexerProperties indexerProperties) {
    this.indexerProperties = indexerProperties;
    this.invertedIndex = new ConcurrentHashMap<>();
    this.stopWords = FileReaderUtils.loadStopWords(indexerProperties.getStopWordsLocation());
  }

  @SneakyThrows
  public void build() {
    List<File> indexingFiles = FileReaderUtils.loadFiles(indexerProperties.getSourceDirectory());
    List<Thread> threads = new ArrayList<>();

    for (List<File> indexingFilesPerThread : IndexerUtils.prepareFilePartitions(indexerProperties.getThreads(), indexingFiles)) {
      Thread indexingThread = new Thread(() -> buildIndex(indexingFilesPerThread));

      threads.add(indexingThread);
      indexingThread.start();
    }

    for (Thread thread : threads) {
      thread.join();
    }

  }

  private void buildIndex(List<File> indexingFiles) {
    for (File file : indexingFiles) {
      String fileName = file.getName();

      List<String> tokens = IndexerUtils.tokenize(
          indexerProperties.getMinimalTokenLength(),
          indexerProperties.getIgnoredDelimiters(),
          file
      );

      for (String token : tokens) {
        if (!stopWords.contains(token)) {
          invertedIndex.computeIfAbsent(token, key -> new TokenDetails())
              .incrementDocumentEntryValue(Document.of(fileName));
        }
      }

    }
  }

}
