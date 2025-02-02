package com.pp.cyan.test.service;

import com.pp.cyan.entity.BlockingStatistic;
import com.pp.cyan.repository.BlockingStatisticRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * Test service to be used to create {@link BlockingStatistic} for testing purposes.
 * Reasons for usage of such class:
 * - separate logic of creation of entity from the test
 * - reusability in different tests
 * - removing transactionality from tests
 */
@Service
@Transactional
public class TestBlockingStatisticService {

  private final BlockingStatisticRepository blockingStatisticRepository;

  public TestBlockingStatisticService(BlockingStatisticRepository blockingStatisticRepository) {
    this.blockingStatisticRepository = blockingStatisticRepository;
  }

  public void deleteAll() {
    blockingStatisticRepository.deleteAll();
  }

  public void saveBlockingStatistics(String category) {
    blockingStatisticRepository.save(createBlockingStatistic(category, new BigInteger("26")));
  }

  public void saveBlockingStatistics(String category, BigInteger count) {
    blockingStatisticRepository.save(createBlockingStatistic(category, count));
  }

  private BlockingStatistic createBlockingStatistic(String category, BigInteger count) {
    BlockingStatistic blockingStatistic = new BlockingStatistic();
    blockingStatistic.setCategory(category);
    blockingStatistic.setCount(count);
    return blockingStatistic;
  }
}
