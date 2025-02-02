package com.pp.cyan.processor;

import com.pp.cyan.controller.dto.CheckDomainRequest;
import com.pp.cyan.controller.dto.CheckDomainResponse;
import com.pp.cyan.entity.BlockingStatistic;
import com.pp.cyan.repository.BlockingStatisticRepository;
import com.pp.cyan.test.service.TestBlockingStatisticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.List;

import static java.math.BigInteger.ONE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DomainProcessorTest {

  @Autowired
  private DomainProcessor domainProcessor;

  @Autowired
  private TestBlockingStatisticService testBlockingStatisticsService;

  @Autowired
  private BlockingStatisticRepository blockingStatisticsRepository;

  @BeforeEach
  void setUp() {
    testBlockingStatisticsService.deleteAll();
  }

  @Test
  void shouldNotFindDomainAndReturnUnknown() {
    CheckDomainRequest request = createCheckDomainRequest("http://www.google.com");

    CheckDomainResponse checkDomainResponse = domainProcessor.checkDomainAndUpdateStatistics(request);

    assertThat(checkDomainResponse).isNotNull();
    assertThat(checkDomainResponse.getDomain()).isEqualTo("www.google.com");
    assertThat(checkDomainResponse.getCategory()).isEqualTo("UNKNOWN");
    assertThat(checkDomainResponse.getBlocked()).isFalse();
  }

  @Test
  void shouldFindNotBlockedDomainAndReturnIt() {
    CheckDomainRequest request = createCheckDomainRequest("http://porn.badinternetdomain.com/test");

    CheckDomainResponse checkDomainResponse = domainProcessor.checkDomainAndUpdateStatistics(request);

    assertThat(checkDomainResponse).isNotNull();
    assertThat(checkDomainResponse.getDomain()).isEqualTo("porn.badinternetdomain.com");
    assertThat(checkDomainResponse.getCategory()).isEqualTo("PORNOGRAPHY");
    assertThat(checkDomainResponse.getBlocked()).isFalse();
  }

  @Test
  void shouldFindBlockedDomainReturnItAndCreateStatistics() {
    CheckDomainRequest request = createCheckDomainRequest("http://sex.badinternetdomain.com/test");

    CheckDomainResponse checkDomainResponse = domainProcessor.checkDomainAndUpdateStatistics(request);

    assertThat(checkDomainResponse).isNotNull();
    assertThat(checkDomainResponse.getDomain()).isEqualTo("sex.badinternetdomain.com");
    assertThat(checkDomainResponse.getCategory()).isEqualTo("PORNOGRAPHY");
    assertThat(checkDomainResponse.getBlocked()).isTrue();
    List<BlockingStatistic> blockingStatistics = blockingStatisticsRepository.findAll();
    assertThat(blockingStatistics).hasSize(1);
    assertThat(blockingStatistics.getFirst().getCategory()).isEqualTo("PORNOGRAPHY");
    assertThat(blockingStatistics.getFirst().getCount().compareTo(ONE)).isEqualTo(0);
  }

  @Test
  void shouldFindBlockedDomainReturnItAndUpdateStatistics() {
    CheckDomainRequest request = createCheckDomainRequest("http://sex.badinternetdomain.com/test");
    testBlockingStatisticsService.saveBlockingStatistics("PORNOGRAPHY");

    CheckDomainResponse checkDomainResponse = domainProcessor.checkDomainAndUpdateStatistics(request);

    assertThat(checkDomainResponse).isNotNull();
    assertThat(checkDomainResponse.getDomain()).isEqualTo("sex.badinternetdomain.com");
    assertThat(checkDomainResponse.getCategory()).isEqualTo("PORNOGRAPHY");
    assertThat(checkDomainResponse.getBlocked()).isTrue();
    List<BlockingStatistic> blockingStatistics = blockingStatisticsRepository.findAll();
    assertThat(blockingStatistics).hasSize(1);
    assertThat(blockingStatistics.getFirst().getCategory()).isEqualTo("PORNOGRAPHY");
    assertThat(blockingStatistics.getFirst().getCount().compareTo(new BigInteger("27"))).isEqualTo(0);
  }

  private CheckDomainRequest createCheckDomainRequest(String url) {
    CheckDomainRequest checkDomainRequest = new CheckDomainRequest();
    checkDomainRequest.setUrl(url);
    return checkDomainRequest;
  }

}