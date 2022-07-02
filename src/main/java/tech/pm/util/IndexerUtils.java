package tech.pm.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class IndexerUtils {

  @SneakyThrows
  public List<String> tokenize(int minTokenLength, String ignoredDelimiters, File file) {
    String fileContent = Files.readString(file.toPath()).replaceAll("\\d", "");

    return Arrays.stream(StringUtils.tokenizeToStringArray(fileContent, ignoredDelimiters))
        .map(String::toLowerCase)
        .filter(token -> token.length() >= minTokenLength)
        .toList();
  }

  public List<List<File>> prepareFilePartitions(int threads, List<File> files) {
    List<List<File>> preparedPartitions = new ArrayList<>();

    for (int i = 0; i < threads; i++) {
      int left =files.size() / threads * i;
      int right = files.size() / threads * (i + 1);

      preparedPartitions.add(getPartitionForResponsibleThread(left, right, files));
    }

    return preparedPartitions;
  }

  private List<File> getPartitionForResponsibleThread(int startIndex, int endIndex, List<File> files) {
    return files.subList(startIndex, endIndex);
  }

}
