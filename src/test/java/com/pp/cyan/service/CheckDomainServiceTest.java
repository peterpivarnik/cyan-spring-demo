package com.pp.cyan.service;

import com.pp.cyan.entity.Domain;
import com.pp.cyan.repository.DomainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckDomainServiceTest {

  @InjectMocks
  private DomainService domainService;

  @Mock
  private DomainRepository domainRepository;

  @Test
  void shouldReturnEmptyOptionalWhenDomainNotExist() {
    String domain = "domain";
    when(domainRepository.findByDomain(domain)).thenReturn(Optional.empty());

    Optional<Domain> domainEntity = domainService.findByDomain(domain);

    assertThat(domainEntity).isNotPresent();
  }

  @Test
  void shouldReturnOptionalWithDomainWhenDomainExist() {
    String domain = "domain";
    when(domainRepository.findByDomain(domain)).thenReturn(Optional.of(createDomain(domain)));

    Optional<Domain> domainEntity = domainService.findByDomain(domain);

    assertThat(domainEntity).isPresent();
    assertThat(domainEntity.get().getDomain()).isEqualTo(domain);
    assertThat(domainEntity.get().getId()).isEqualTo(BigInteger.ONE);
    assertThat(domainEntity.get().getBlocked()).isFalse();
  }

  private Domain createDomain(String domain) {
    Domain domainEntity = new Domain();
    domainEntity.setId(BigInteger.ONE);
    domainEntity.setDomain(domain);
    domainEntity.setBlocked(false);
    domainEntity.setCategory("category");
    return domainEntity;
  }
}