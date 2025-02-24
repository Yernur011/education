package com.example.authserver.service.crud.impl;

import com.example.authserver.domain.dto.test.TestDto;
import com.example.authserver.domain.entity.edu.Test;
import com.example.authserver.domain.repository.edu.TestRepository;
import com.example.authserver.service.crud.TestCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.authserver.utils.codes.ErrorCode.TEST_NOT_FOUND;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestCrudServiceImpl implements TestCrudService {
    private final TestRepository testRepository;

    @Override
    public List<TestDto> findAllTest(Long page, Long size) {
        return testRepository.findAll(PageRequest.of(page.intValue(), size.intValue()))
                .getContent()
                .stream()
                .map(test ->
                        new TestDto(test.getId(),
                                test.getTitle(),
                                test.getState(),
                                test.getType())
                )
                .toList();
    }

    @Override
    public TestDto findDtoById(Long id) {
        return testRepository.findById(id)
                .map(test ->
                        new TestDto(
                                test.getId(),
                                test.getTitle(),
                                test.getState(),
                                test.getType())
                ).orElseThrow(() -> new NoSuchElementException(TEST_NOT_FOUND));
    }

    @Override
    public Optional<Test> findById(Long id) {
        return testRepository.findById(id);
    }

}
