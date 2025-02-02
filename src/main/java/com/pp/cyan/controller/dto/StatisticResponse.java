package com.pp.cyan.controller.dto;

import java.math.BigInteger;

public class StatisticResponse {

  private BigInteger numberOfMalwareAndPhishingBlocked;
  private BigInteger numberOfOthersBlocked;

  public BigInteger getNumberOfMalwareAndPhishingBlocked() {
    return numberOfMalwareAndPhishingBlocked;
  }

  public void setNumberOfMalwareAndPhishingBlocked(BigInteger numberOfMalwareAndPhishingBlocked) {
    this.numberOfMalwareAndPhishingBlocked = numberOfMalwareAndPhishingBlocked;
  }

  public BigInteger getNumberOfOthersBlocked() {
    return numberOfOthersBlocked;
  }

  public void setNumberOfOthersBlocked(BigInteger numberOfOthersBlocked) {
    this.numberOfOthersBlocked = numberOfOthersBlocked;
  }
}
