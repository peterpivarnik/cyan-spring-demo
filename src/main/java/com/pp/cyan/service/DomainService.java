package com.pp.cyan.service;

import com.pp.cyan.entity.Domain;
import com.pp.cyan.repository.DomainRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service providing functionality for {@link Domain} entity.
 */
@Service
public class DomainService {

  private final DomainRepository domainRepository;

  public DomainService(DomainRepository domainRepository) {
    this.domainRepository = domainRepository;
  }

  /**
   * Finds {@link Domain} entity for provided primary key.
   *
   * @param domain provided primary key
   * @return Found {@link Domain} entity
   */
  public Optional<Domain> findByDomain(String domain) {
    return domainRepository.findByDomain(domain);
  }

}
