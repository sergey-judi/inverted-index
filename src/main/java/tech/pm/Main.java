package tech.pm;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tech.pm.domain.Indexer;

public class Main {

  public static void main(String[] args) {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(IndexerConfig.class);
    Indexer indexer = applicationContext.getBean(Indexer.class);
    indexer.build();

  }

}
