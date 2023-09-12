package com.tapTest.springboot.repository;

import com.tapTest.springboot.domain.Program;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProgramRepositoryTest {

    @Autowired
    private ProgramRepository programRepository;

    @Test
    void findAll_should_return_program_list() {
        // When
        List<Program> programs = this.programRepository.findAll();
        // Then
        assertEquals(2, programs.size());
    }

    @Test
    void findById_should_return_program() {
        // When
        Optional<Program> program = this.programRepository.findById(1L);
        // Then
        assertTrue(program.isPresent());
    }

    @Test
    void save_should_insert_new_program() {
        // Given
        Program newProgram = new Program();
        newProgram.setName("Natural Science");
        newProgram.setProgramKey("n");
        newProgram.setPassingScore(160);
        // When
        Program persistedProgram = this.programRepository.save(newProgram);
        // Then
        assertNotNull(persistedProgram);
        assertEquals(3, persistedProgram.getId());
    }

    @Test
    void save_should_update_existing_program() {
        // Given
        Program existingProgram = new Program();
        existingProgram.setId(3);
        existingProgram.setName("Education");
        existingProgram.setProgramKey("e");
        existingProgram.setPassingScore(190);
        // When
        Program updatedProgram = this.programRepository.save(existingProgram);
        // Then
        assertNotNull(updatedProgram);
        assertEquals("Education", updatedProgram.getName());
        assertEquals("e", updatedProgram.getProgramKey());
        assertEquals(190, updatedProgram.getPassingScore());
    }

    @Test
    void deleteById_should_delete_program() {
        // When
        this.programRepository.deleteById(3L);
        Optional<Program> program = this.programRepository.findById(3L);
        // Then
        assertFalse(program.isPresent());
    }
}
