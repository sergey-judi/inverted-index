package tech.pm.model;

import lombok.Value;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Value
public class TokenDetails {

  ConcurrentHashMap<Document, AtomicLong> documentEntries;

  public TokenDetails() {
    this.documentEntries = new ConcurrentHashMap<>();
  }

  public Long incrementDocumentEntryValue(Document document) {
   return documentEntries.computeIfAbsent(document, key -> new AtomicLong())
        .incrementAndGet();
  }

}
