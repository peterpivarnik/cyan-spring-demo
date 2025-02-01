package com.pp.cyan.ad.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigInteger;

/**
 * Entity representing db table domain.
 */
@Entity
@Table(name = "domain")
public class Domain {

  @Id
  @Column(name = "id", nullable = false)
  private BigInteger id;

  @Column(name = "domain", nullable = false)
  private String domain;

  @Column(name = "category", nullable = false)
  private String category;

  @Column(name = "blocked" , nullable = false)
  private Boolean blocked;

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Boolean getBlocked() {
    return blocked;
  }

  public void setBlocked(Boolean blocked) {
    this.blocked = blocked;
  }
}
