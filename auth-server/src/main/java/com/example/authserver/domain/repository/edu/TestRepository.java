package com.example.authserver.domain.repository.edu;

import com.example.authserver.domain.entity.edu.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
    Page<Test> findAll(Pageable pageable);
}
