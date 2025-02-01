package com.pp.cyan.ad.repository;

import com.pp.cyan.ad.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for {@link Domain} entity.
 */
@Repository
public interface DomainRepository extends JpaRepository<Domain, String> {

  Optional<Domain> findByDomain(String host);
}
