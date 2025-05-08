package com.github.classpick.default_file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefaultFileRepository extends JpaRepository<DefaultFileEntity, Long> {

    List<DefaultFileEntity> findAllByUser_UserId(Long userId);
}
