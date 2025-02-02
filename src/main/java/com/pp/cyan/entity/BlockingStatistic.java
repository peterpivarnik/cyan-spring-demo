package com.pp.cyan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigInteger;

/**
 * Entity representing db table BLOCKING_STATISTIC.
 */
@Entity
@Table(name = "BLOCKING_STATISTIC")
public class BlockingStatistic {

  @Id
  @Column(name = "category", nullable = false)
  private String category;

  @Column(name="count", nullable = false)
  private BigInteger count;

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public BigInteger getCount() {
    return count;
  }

  public void setCount(BigInteger count) {
    this.count = count;
  }
}
