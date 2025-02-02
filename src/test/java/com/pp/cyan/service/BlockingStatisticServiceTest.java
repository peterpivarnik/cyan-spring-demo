package com.pp.cyan.service;

import com.pp.cyan.controller.dto.StatisticResponse;
import com.pp.cyan.entity.BlockingStatistic;
import com.pp.cyan.repository.BlockingStatisticRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.math.BigInteger.ONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlockingStatisticServiceTest {

  @InjectMocks
  private BlockingStatisticService blockingStatisticsService;

  @Mock
  private BlockingStatisticRepository blockingStatisticRepository;

  @Captor
  ArgumentCaptor<BlockingStatistic> blockingStatisticsCaptor;

  @Test
  void shouldCreateBlockingStatisticsWhenNotExists() {
    String category = "DRUGS";

    when(blockingStatisticRepository.findById(category)).thenReturn(Optional.empty());

    blockingStatisticsService.updateStatistics(category);

    verify(blockingStatisticRepository).save(blockingStatisticsCaptor.capture());
    BlockingStatistic blockingStatistics = blockingStatisticsCaptor.getValue();
    assertThat(blockingStatistics).isNotNull();
    assertThat(blockingStatistics.getCount().compareTo(ONE)).isEqualTo(0);
    assertThat(blockingStatistics.getCategory()).isEqualTo(category);
  }

  @Test
  void shouldUpdateBlockingStatisticWhenExists() {
    String category = "DRUGS";

    when(blockingStatisticRepository.findById(category)).thenReturn(Optional.of(createBlockingStatistics("DRUGS")));

    blockingStatisticsService.updateStatistics(category);

    verify(blockingStatisticRepository).save(blockingStatisticsCaptor.capture());
    BlockingStatistic blockingStatistics = blockingStatisticsCaptor.getValue();
    assertThat(blockingStatistics).isNotNull();
    assertThat(blockingStatistics.getCount().compareTo(new BigInteger("26"))).isEqualTo(0);
    assertThat(blockingStatistics.getCategory()).isEqualTo(category);
  }

  private BlockingStatistic createBlockingStatistics(String drugs) {
    BlockingStatistic blockingStatistics = new BlockingStatistic();
    blockingStatistics.setCategory(drugs);
    blockingStatistics.setCount(new BigInteger("25"));
    return blockingStatistics;
  }

  @Test
  void shouldGetStatistics() {
    when(blockingStatisticRepository.findAll()).thenReturn(createBlockingStatisticsList());

    StatisticResponse statistics = blockingStatisticsService.getStatistics();

    assertThat(statistics).isNotNull();
    assertThat(statistics.getNumberOfMalwareAndPhishingBlocked()).isEqualByComparingTo(new BigInteger("25"));
    assertThat(statistics.getNumberOfOthersBlocked()).isEqualByComparingTo(new BigInteger("50"));
  }

  private List<BlockingStatistic> createBlockingStatisticsList() {
    List<BlockingStatistic> blockingStatistics = new ArrayList<>();
    blockingStatistics.add(createBlockingStatistics("DRUGS"));
    blockingStatistics.add(createBlockingStatistics("GAMBLING"));
    blockingStatistics.add(createBlockingStatistics("MALWARE_AND_PHISHING"));
    return blockingStatistics;
  }
}