package com.tapTest.springboot.repository;

import com.tapTest.springboot.domain.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    void findAll_should_return_subject_list() {
        // When
        List<Subject> subjects = this.subjectRepository.findAll();
        // Then
        assertEquals(5, subjects.size());
    }

    @Test
    void findById_should_return_subject() {
        // When
        Optional<Subject> subject = this.subjectRepository.findById(2L);
        // Then
        assertTrue(subject.isPresent());
    }

    @Test
    void save_should_insert_new_subject() {
        // Given
        Subject newSubject = new Subject();
        newSubject.setName("Biology");
        newSubject.setFileOrder(6);
        // When
        Subject persistedSubject = this.subjectRepository.save(newSubject);
        // Then
        assertNotNull(persistedSubject);
        assertEquals(6, persistedSubject.getId());
    }

    @Test
    void save_should_update_existing_subject() {
        // Given
        Subject existingSubject = new Subject();
        existingSubject.setId(6);
        existingSubject.setName("Physics");
        existingSubject.setFileOrder(7);
        // When
        Subject updatedSubject = this.subjectRepository.save(existingSubject);
        // Then
        assertNotNull(updatedSubject);
        assertEquals("Physics", updatedSubject.getName());
        assertEquals(7, updatedSubject.getFileOrder());
    }

    @Test
    void deleteById_should_delete_subject() {
        // When
        this.subjectRepository.deleteById(6L);
        Optional<Subject> subject = this.subjectRepository.findById(6L);
        // Then
        assertFalse(subject.isPresent());
    }
}
