package com.pp.cyan.repository;

import com.pp.cyan.entity.Domain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DomainRepositoryTest {

  @Autowired
  private DomainRepository domainRepository;

  @Test
  void shouldFindByDomain() {
    String domain = "gambling.badinternetdomain.com";

    Optional<Domain> domainOptional = domainRepository.findByDomain(domain);

    assertThat(domainOptional).isPresent();
    assertThat(domainOptional.get().getDomain()).isEqualTo(domain);
  }
}