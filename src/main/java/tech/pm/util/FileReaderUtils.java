package tech.pm.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@UtilityClass
public class FileReaderUtils {

  @SneakyThrows
  public List<File> loadFiles(String fileName) {
    try (Stream<Path> resourceStream = Files.walk(getPathFromFileName(fileName))) {

      return resourceStream.filter(Files::isRegularFile)
          .map(Path::toFile)
          .toList();
    }
  }

  @SneakyThrows
  public Set<String> loadStopWords(String fileName) {
    return Set.copyOf(
        Files.readAllLines(
            getPathFromFileName(fileName)
        )
    );
  }

  @SneakyThrows
  private Path getPathFromFileName(String fileName) {
    return ResourceUtils.getFile(fileName).toPath();
  }

}
