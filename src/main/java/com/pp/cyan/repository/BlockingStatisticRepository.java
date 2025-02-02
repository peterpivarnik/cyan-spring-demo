package com.pp.cyan.repository;

import com.pp.cyan.entity.BlockingStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for {@link BlockingStatistic} entity.
 */
@Repository
public interface BlockingStatisticRepository extends JpaRepository<BlockingStatistic, String> {

}
