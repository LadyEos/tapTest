package com.tapTest.springboot.service;

import com.tapTest.springboot.domain.Program;
import com.tapTest.springboot.domain.Subject;
import com.tapTest.springboot.repository.ProgramRepository;
import com.tapTest.springboot.repository.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProgramServiceImplTest {

    @Mock

    private ProgramRepository programRepository;
    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private ProgramServiceImpl programService;
    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Test
    void findAll_should_return_program_list() {
        // Given
        Program program = this.buildTestProgram();
        // When
        when(programRepository.findAll()).thenReturn(List.of(program));
        List<Program> programs = this.programService.getPrograms();
        // Then
        assertEquals(1, programs.size());
        verify(this.programRepository).findAll();
    }

    @Test
    void findById_should_return_program() throws Exception {
        // Given
        Program program = this.buildTestProgram();
        // When
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        Program returnedProgram = this.programService.getProgram(1L);
        // Then
        assertEquals(program.getId(), returnedProgram.getId());
        verify(this.programRepository).findById(1L);
    }

    @Test
    void save_should_insert_new_program() {
        // Given
        Program program = this.buildTestProgram();
        // When
        this.programService.createProgram(program);
        // Then
        verify(this.programRepository).save(program);
    }

    @Test
    void save_should_update_program() throws Exception {
        // Given
        Program program = this.buildTestProgram();
        // When
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        // Then
        assertEquals("s", program.getProgramKey());
        assertEquals("Science", program.getName());
        // When
        Program returnedProgram = this.programService.getProgram(1L);
        returnedProgram.setName("Humanities");
        returnedProgram.setPassingScore(200);
        returnedProgram.setProgramKey("l");
        this.programService.updateProgram((long) returnedProgram.getId(),returnedProgram);
        // Then
        assertEquals("l", program.getProgramKey());
        assertEquals("Humanities", program.getName());
        verify(this.programRepository).save(returnedProgram);
    }

    @Test
    void deleteById_should_delete_employee() throws Exception {
        // Given
        Program program = this.buildTestProgram();
        // When
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        // Then
        assertEquals("s", program.getProgramKey());
        assertEquals("Science", program.getName());
        
        // When
        this.programService.deleteProgram(1L);
        // Then
        verify(this.programRepository).deleteById(1L);
    }

    @Test
    void addSubject_should_add_subject_program() throws Exception {
        // When
        when(programRepository.findById(1L)).thenReturn(Optional.of(this.buildTestProgram()));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(this.buildTestSubject()));
        Program program = this.programService.getProgram(1L);
        Subject subject = this.subjectService.getSubject(1L);

        this.programService.addSubject((long) program.getId(), (long) subject.getId());
        // Then
        verify(this.programRepository).save(program);
    }

    @Test
    void delete_should_remove_subject_from_program() throws Exception {
        // When
        when(programRepository.findById(1L)).thenReturn(Optional.of(this.buildTestProgram()));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(this.buildTestSubject()));
        Program program = this.programService.getProgram(1L);
        Subject subject = this.subjectService.getSubject(1L);

        this.programService.addSubject((long) program.getId(), (long) subject.getId());

        this.programService.removeSubject((long) program.getId(), (long) subject.getId());
        // Then
        verify(this.programRepository, atLeast(1)).save(program);
    }

    @Test
    void get_should_find_subject_from_program() throws Exception {
        // When
        when(programRepository.findById(1L)).thenReturn(Optional.of(this.buildTestProgram()));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(this.buildTestSubject()));
        Program program = this.programService.getProgram(1L);
        Subject subject = this.subjectService.getSubject(1L);

        Set<Subject> subjects = this.programService.getSubjects((long) program.getId());
        // Then
        assertEquals(0, subjects.size());
        // When
        this.programService.addSubject((long) program.getId(), (long) subject.getId());
        // Then
        subjects = this.programService.getSubjects((long) program.getId());
        // Then
        assertEquals(1, subjects.size());
        verify(this.programRepository).save(program);
    }

    private Program buildTestProgram() {
        Program program = new Program();
        program.setId(1);
        program.setName("Science");
        program.setProgramKey("s");
        program.setPassingScore(160);
        return program;
    }

    private Subject buildTestSubject() {
        Subject subject = new Subject();
        subject.setId(1);
        subject.setName("English");
        subject.setFileOrder(1);
        return subject;
    }
}
