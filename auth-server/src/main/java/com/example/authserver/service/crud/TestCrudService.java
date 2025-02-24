package com.example.authserver.service.crud;

import com.example.authserver.domain.dto.test.TestDto;
import com.example.authserver.domain.entity.edu.Test;

import java.util.List;
import java.util.Optional;

public interface TestCrudService {
    List<TestDto> findAllTest(Long page, Long size);

    TestDto findDtoById(Long id);
    Optional<Test> findById(Long id);

}
