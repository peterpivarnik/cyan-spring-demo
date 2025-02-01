package com.pp.cyan.ad.service;

import com.pp.cyan.ad.controller.dto.CheckDomainRequest;
import com.pp.cyan.ad.controller.dto.CheckDomainResponse;
import com.pp.cyan.ad.entity.Domain;
import com.pp.cyan.ad.repository.DomainRepository;
import org.springframework.stereotype.Service;

import java.net.URI;

import static java.lang.Boolean.FALSE;

/**
 * Service providing functionality for {@link Domain} entity.
 */
@Service
public class CheckDomainService {

  private final DomainRepository domainRepository;

  public CheckDomainService(DomainRepository domainRepository) {
    this.domainRepository = domainRepository;
  }

  /**
   * Checks whether provided url should be blocked or not.
   *
   * @param request request containing url to be checked
   * @return response if provided url should be blocked
   */
  public CheckDomainResponse checkDomain(CheckDomainRequest request) {
    URI uri = URI.create(request.getUrl());
    String host = uri.getHost();
    return domainRepository.findByDomain(host)
                           .map(this::getCheckDomainResponse)
                           .orElseGet(() -> getCheckDomainResponse(host, "UNKNOWN", FALSE));
  }

  private CheckDomainResponse getCheckDomainResponse(Domain domain) {
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
