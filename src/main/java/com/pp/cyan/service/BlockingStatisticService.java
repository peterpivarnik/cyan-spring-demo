package com.pp.cyan.service;

import com.pp.cyan.controller.dto.StatisticResponse;
import com.pp.cyan.entity.BlockingStatistic;
import com.pp.cyan.repository.BlockingStatisticRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

import static java.math.BigInteger.ONE;

@Service
public class BlockingStatisticService {

  private final BlockingStatisticRepository blockingStatisticRepository;

  public BlockingStatisticService(BlockingStatisticRepository blockingStatisticRepository) {
    this.blockingStatisticRepository = blockingStatisticRepository;
  }

  public synchronized void updateStatistics(String category) {
    blockingStatisticRepository.findById(category)
                               .ifPresentOrElse(this::updateBlockingStatistic, () -> createBlockingStatistic(category));
  }

  private void updateBlockingStatistic(BlockingStatistic blockingStatistic) {
    BigInteger count = blockingStatistic.getCount();
    blockingStatistic.setCount(count.add(ONE));
    blockingStatisticRepository.save(blockingStatistic);
  }

  private void createBlockingStatistic(String category) {
    BlockingStatistic blockingStatistic = new BlockingStatistic();
    blockingStatistic.setCategory(category);
    blockingStatistic.setCount(ONE);
    blockingStatisticRepository.save(blockingStatistic);
  }

  public StatisticResponse getStatistics() {
    List<BlockingStatistic> blockingStatistic = blockingStatisticRepository.findAll();
    BigInteger allCounts = blockingStatistic
        .stream()
        .map(BlockingStatistic::getCount)
        .reduce(BigInteger.ZERO, BigInteger::add);
    BigInteger malwareAndPhishing = blockingStatistic.stream()
                                                       .filter(statistic -> statistic.getCategory().equals("MALWARE_AND_PHISHING"))
                                                       .map(BlockingStatistic::getCount)
                                                       .findFirst()
                                                       .orElse(BigInteger.ZERO);
    return getStatisticResponse(malwareAndPhishing, allCounts.subtract(malwareAndPhishing));
  }

  private StatisticResponse getStatisticResponse(BigInteger malwareAndPhishing, BigInteger others) {
    StatisticResponse statisticResponse = new StatisticResponse();
    statisticResponse.setNumberOfMalwareAndPhishingBlocked(malwareAndPhishing);
    statisticResponse.setNumberOfOthersBlocked(others);
    return statisticResponse;
  }
}
