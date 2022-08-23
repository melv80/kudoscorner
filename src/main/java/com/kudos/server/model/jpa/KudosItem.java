package com.kudos.server.model.jpa;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@javax.persistence.Entity
@Table
@Inheritance(
    strategy = InheritanceType.TABLE_PER_CLASS
)
public abstract class KudosItem {
  @Id
  @GeneratedValue()
  @Column(name = "id", unique = true)
  private Long id;

  private Instant created = Instant.now();

  @UpdateTimestamp
  private Instant edited = Instant.now();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Instant getEdited() {
    return edited;
  }


  public Instant getCreated() {
    return created;
  }

  public void setCreated(Instant created) {
    this.created = created;
  }


  public void setEdited(Instant edited) {
    this.edited = edited;
  }
}
