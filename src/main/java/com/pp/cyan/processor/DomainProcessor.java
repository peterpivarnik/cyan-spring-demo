package com.pp.cyan.processor;

import com.pp.cyan.controller.dto.CheckDomainRequest;
import com.pp.cyan.controller.dto.CheckDomainResponse;
import com.pp.cyan.entity.Domain;
import com.pp.cyan.service.BlockingStatisticService;
import com.pp.cyan.service.DomainService;
import org.springframework.stereotype.Service;

import java.net.URI;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * This class is combining functionality from {@link BlockingStatisticService} and {@link DomainService} to check domain of provided url.
 * If provided url is blocked also statistics for related category is updated.
 */
@Service
public class DomainProcessor {

  private final BlockingStatisticService blockingStatisticsService;

  private final DomainService domainService;

  public DomainProcessor(BlockingStatisticService blockingStatisticsService, DomainService domainService) {
    this.blockingStatisticsService = blockingStatisticsService;
    this.domainService = domainService;
  }

  /**
   * Checks domain of provide url and update statistics if necessary.
   *
   * @param request request containing url to be checked
   * @return response whether provided url is blocked.
   */
  public CheckDomainResponse checkDomainAndUpdateStatistics(CheckDomainRequest request) {
    URI uri = URI.create(request.getUrl());
    String host = uri.getHost();
    return domainService.findByDomain(host)
                        .map(this::processDomain)
                        .orElseGet(() -> getCheckDomainResponse(host, "UNKNOWN", FALSE));
  }

  private CheckDomainResponse processDomain(Domain domain) {
    if (TRUE.equals(domain.getBlocked())) {
      blockingStatisticsService.updateStatistics(domain.getCategory());
    }
    return getCheckDomainResponse(domain.getDomain(), domain.getCategory(), domain.getBlocked());
  }

  private CheckDomainResponse getCheckDomainResponse(String domain, String category, Boolean blocked) {
    CheckDomainResponse checkDomainResponse = new CheckDomainResponse();
    checkDomainResponse.setDomain(domain);
    checkDomainResponse.setCategory(category);
    checkDomainResponse.setBlocked(blocked);
    return checkDomainResponse;
  }
}
