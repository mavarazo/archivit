package com.mav.archivit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tags")
public class Tag {

  @Id @GeneratedValue private Long id;
  private String name;
  private int value;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public static final class TagBuilder {
    private Tag tag;

    private TagBuilder() {
      tag = new Tag();
    }

    public static TagBuilder aTag() {
      return new TagBuilder();
    }

    public TagBuilder withId(long id) {
      tag.setId(id);
      return this;
    }

    public TagBuilder withName(String name) {
      tag.setName(name);
      return this;
    }

    public TagBuilder withValue(int value) {
      tag.setValue(value);
      return this;
    }

    public Tag build() {
      return tag;
    }
  }
}
