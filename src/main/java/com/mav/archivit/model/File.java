package com.mav.archivit.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "files")
@EntityListeners(AuditingEntityListener.class)
public class File {

  @Id @GeneratedValue private Long id;

  @CreatedDate
  @Temporal(TIMESTAMP)
  private Date created;

  @LastModifiedDate
  @Temporal(TIMESTAMP)
  private Date updated;

  @ManyToOne @JsonBackReference private User user;

  private String name;
  private String path;
  private long size;

  @Column(columnDefinition = "TEXT")
  private String content;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "file_tag",
      joinColumns = @JoinColumn(name = "file_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
  private List<Tag> tags = new ArrayList<>();

  @OneToMany(mappedBy = "parentFile")
  @JsonBackReference
  private List<File> children = new ArrayList<>();

  @ManyToOne @JsonManagedReference private File parentFile;

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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public List<File> getChildren() {
    return children;
  }

  public void setChildren(List<File> children) {
    this.children = children;
  }

  public File getParentFile() {
    return parentFile;
  }

  public void setParentFile(File parentFile) {
    this.parentFile = parentFile;
  }

  public static final class FileBuilder {
    private File file;

    private FileBuilder() {
      file = new File();
    }

    public static FileBuilder aFile() {
      return new FileBuilder();
    }

    public FileBuilder withId(Long id) {
      file.setId(id);
      return this;
    }

    public FileBuilder withCreated(Date created) {
      file.setCreated(created);
      return this;
    }

    public FileBuilder withUpdated(Date updated) {
      file.setUpdated(updated);
      return this;
    }

    public FileBuilder withUser(User user) {
      file.setUser(user);
      return this;
    }

    public FileBuilder withName(String name) {
      file.setName(name);
      return this;
    }

    public FileBuilder withPath(String path) {
      file.setPath(path);
      return this;
    }

    public FileBuilder withSize(long size) {
      file.setSize(size);
      return this;
    }

    public FileBuilder withContent(String content) {
      file.setContent(content);
      return this;
    }

    public FileBuilder withTags(List<Tag> tags) {
      file.setTags(tags);
      return this;
    }

    public FileBuilder withChildren(List<File> children) {
      file.setChildren(children);
      return this;
    }

    public FileBuilder withParentFile(File parentFile) {
      file.setParentFile(parentFile);
      return this;
    }

    public File build() {
      return file;
    }
  }
}
