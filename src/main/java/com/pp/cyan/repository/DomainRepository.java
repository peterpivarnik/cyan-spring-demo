package com.pp.cyan.repository;

import com.pp.cyan.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA repository for {@link Domain} entity.
 */
@Repository
public interface DomainRepository extends JpaRepository<Domain, String> {

  Optional<Domain> findByDomain(String host);
}
