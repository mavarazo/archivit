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
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "files")
@EntityListeners(AuditingEntityListener.class)
public class File {

  @Id @GeneratedValue private Long id;

  @CreatedDate private LocalDateTime created;

  @LastModifiedDate private LocalDateTime updated;

  @NotNull @ManyToOne @JsonBackReference private User user;

  @NotNull private String name;
  private String extension;
  @NotNull private String path;
  @NotNull private Boolean isRegularFile = Boolean.TRUE;
  @NotNull private LocalDateTime creationTime;
  @NotNull private LocalDateTime lastAccessTime;
  @NotNull private LocalDateTime lastModifiedTime;
  @NotNull private String fileKey;
  private long size;

  @Column(columnDefinition = "TEXT")
  private String content;

  @OneToMany(mappedBy = "parentFile")
  @JsonManagedReference
  private List<File> children = new ArrayList<>();

  @ManyToOne @JsonBackReference private File parentFile;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "file_tag",
      joinColumns = @JoinColumn(name = "file_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
  private List<Tag> tags = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public LocalDateTime getUpdated() {
    return updated;
  }

  public void setUpdated(LocalDateTime updated) {
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

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Boolean getRegularFile() {
    return isRegularFile;
  }

  public void setRegularFile(Boolean regularFile) {
    isRegularFile = regularFile;
  }

  public LocalDateTime getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(LocalDateTime creationTime) {
    this.creationTime = creationTime;
  }

  public LocalDateTime getLastAccessTime() {
    return lastAccessTime;
  }

  public void setLastAccessTime(LocalDateTime lastAccessTime) {
    this.lastAccessTime = lastAccessTime;
  }

  public LocalDateTime getLastModifiedTime() {
    return lastModifiedTime;
  }

  public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
  }

  public String getFileKey() {
    return fileKey;
  }

  public void setFileKey(String fileKey) {
    this.fileKey = fileKey;
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

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
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

    public FileBuilder withCreated(LocalDateTime created) {
      file.setCreated(created);
      return this;
    }

    public FileBuilder withUpdated(LocalDateTime updated) {
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

    public FileBuilder withExtension(String extension) {
      file.setExtension(extension);
      return this;
    }

    public FileBuilder withPath(String path) {
      file.setPath(path);
      return this;
    }

    public FileBuilder withCreationTime(LocalDateTime creationTime) {
      file.setCreationTime(creationTime);
      return this;
    }

    public FileBuilder withLastAccessTime(LocalDateTime lastAccessTime) {
      file.setLastAccessTime(lastAccessTime);
      return this;
    }

    public FileBuilder withLastModifiedTime(LocalDateTime lastModifiedTime) {
      file.setLastModifiedTime(lastModifiedTime);
      return this;
    }

    public FileBuilder withFileKey(String fileKey) {
      file.setFileKey(fileKey);
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

    public FileBuilder withChildren(List<File> children) {
      file.setChildren(children);
      return this;
    }

    public FileBuilder withParentFile(File parentFile) {
      file.setParentFile(parentFile);
      return this;
    }

    public FileBuilder withTags(List<Tag> tags) {
      file.setTags(tags);
      return this;
    }

    public File build() {
      return file;
    }
  }
}
