package com.github.classpick.file.default_file.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultFileRepository extends JpaRepository<DefaultFileEntity, Long> {

    List<DefaultFileEntity> findAllByUser_UserId(Long userId);
}
