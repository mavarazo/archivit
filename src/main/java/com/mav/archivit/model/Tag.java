package com.mav.archivit.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tags")
public class Tag {

  @Id @GeneratedValue private Long id;
  private String name;
  private int value;
  @NotNull @ManyToOne @JsonBackReference private User user;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public static final class TagBuilder {
    private Tag tag;

    private TagBuilder() {
      tag = new Tag();
    }

    public static TagBuilder aTag() {
      return new TagBuilder();
    }

    public TagBuilder withId(Long id) {
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

    public TagBuilder withUser(User user) {
      tag.setUser(user);
      return this;
    }

    public Tag build() {
      return tag;
    }
  }
}
