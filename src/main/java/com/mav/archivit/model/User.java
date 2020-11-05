package com.mav.archivit.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

  @Id @GeneratedValue private Long id;

  @CreatedDate
  @Temporal(TIMESTAMP)
  private Date created;

  @LastModifiedDate
  @Temporal(TIMESTAMP)
  private Date updated;

  private String name;
  private String path;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public static final class UserBuilder {
    private User user;

    private UserBuilder() {
      user = new User();
    }

    public static UserBuilder anUser() {
      return new UserBuilder();
    }

    public UserBuilder withId(Long id) {
      user.setId(id);
      return this;
    }

    public UserBuilder withCreated(Date created) {
      user.setCreated(created);
      return this;
    }

    public UserBuilder withUpdated(Date updated) {
      user.setUpdated(updated);
      return this;
    }

    public UserBuilder withName(String name) {
      user.setName(name);
      return this;
    }

    public UserBuilder withPath(String path) {
      user.setPath(path);
      return this;
    }

    public User build() {
      return user;
    }
  }
}
