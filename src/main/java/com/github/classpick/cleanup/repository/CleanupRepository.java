package com.github.classpick.cleanup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CleanupRepository extends JpaRepository<CleanupEntity, Long> {
}
