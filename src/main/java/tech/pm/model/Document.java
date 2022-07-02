package tech.pm.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class Document {

  String name;

}
