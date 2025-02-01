package com.pp.cyan.ad.service;

import com.pp.cyan.ad.controller.dto.CheckDomainRequest;
import com.pp.cyan.ad.controller.dto.CheckDomainResponse;
import com.pp.cyan.ad.entity.Domain;
import com.pp.cyan.ad.repository.DomainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckDomainServiceTest {

  @InjectMocks
  private CheckDomainService checkDomainService;

  @Mock
  private DomainRepository domainRepository;

  @Test
  void shouldReturnResponseWithUnknownCategory() {
    String host = "www.instagram.com";
    CheckDomainRequest request = createCheckDomainRequest();

    when(domainRepository.findByDomain(host)).thenReturn(Optional.empty());

    CheckDomainResponse checkDomainResponse = checkDomainService.checkDomain(request);

    assertThat(checkDomainResponse).isNotNull();
    assertThat(checkDomainResponse.getDomain()).isEqualTo(host);
    assertThat(checkDomainResponse.getBlocked()).isFalse();
    assertThat(checkDomainResponse.getCategory()).isEqualTo("UNKNOWN");
  }

  @Test
  void shouldReturnResponseWithKnownCategory() {
    String host = "www.instagram.com";
    CheckDomainRequest request = createCheckDomainRequest();

    when(domainRepository.findByDomain(host)).thenReturn(Optional.of(createDomain(host)));

    CheckDomainResponse checkDomainResponse = checkDomainService.checkDomain(request);

    assertThat(checkDomainResponse).isNotNull();
    assertThat(checkDomainResponse.getDomain()).isEqualTo(host);
    assertThat(checkDomainResponse.getBlocked()).isTrue();
    assertThat(checkDomainResponse.getCategory()).isEqualTo("SOCIAL_MEDIA");
  }

  private Domain createDomain(String domainParam) {
    Domain domain = new Domain();
    domain.setDomain(domainParam);
    domain.setBlocked(true);
    domain.setCategory("SOCIAL_MEDIA");
    return domain;
  }

  private CheckDomainRequest createCheckDomainRequest() {
    CheckDomainRequest checkDomainRequest = new CheckDomainRequest();
    checkDomainRequest.setUrl("https://www.instagram.com/cyandigitalsecurity");
    return checkDomainRequest;
  }

}