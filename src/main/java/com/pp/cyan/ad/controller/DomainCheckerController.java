package com.pp.cyan.ad.controller;

import com.pp.cyan.ad.controller.dto.CheckDomainRequest;
import com.pp.cyan.ad.controller.dto.CheckDomainResponse;
import com.pp.cyan.ad.service.CheckDomainService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller.
 */
@RestController
public class DomainCheckerController {

  private final CheckDomainService checkDomainService;

  public DomainCheckerController(CheckDomainService checkDomainService) {
    this.checkDomainService = checkDomainService;
  }

  @PostMapping(value = "/cyanapp/api/domain-checker")
  public CheckDomainResponse checkDomain(@RequestBody CheckDomainRequest checkDomainRequest) {
    return checkDomainService.checkDomain(checkDomainRequest);
  }

}
