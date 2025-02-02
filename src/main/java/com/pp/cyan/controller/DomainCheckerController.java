package com.pp.cyan.controller;

import com.pp.cyan.controller.dto.CheckDomainRequest;
import com.pp.cyan.controller.dto.CheckDomainResponse;
import com.pp.cyan.controller.dto.StatisticResponse;
import com.pp.cyan.processor.DomainProcessor;
import com.pp.cyan.service.BlockingStatisticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller.
 */
@RestController
public class DomainCheckerController {

  private final DomainProcessor domainProcessor;
  private final BlockingStatisticService blockingStatisticsService;

  public DomainCheckerController(DomainProcessor domainProcessor, BlockingStatisticService blockingStatisticsService) {
    this.domainProcessor = domainProcessor;
    this.blockingStatisticsService = blockingStatisticsService;
  }

  @PostMapping(value = "/cyanapp/api/domain-checker")
  public CheckDomainResponse checkDomain(@RequestBody CheckDomainRequest checkDomainRequest) {
    return domainProcessor.checkDomainAndUpdateStatistics(checkDomainRequest);
  }

  @GetMapping(value = "/cyanapp/api/domain-checker/statistics")
  public StatisticResponse getStatistics() {
    return blockingStatisticsService.getStatistics();
  }
}
