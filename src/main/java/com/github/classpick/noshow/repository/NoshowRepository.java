package com.github.classpick.noshow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoshowRepository extends JpaRepository<NoshowEntity, Long> {
}
