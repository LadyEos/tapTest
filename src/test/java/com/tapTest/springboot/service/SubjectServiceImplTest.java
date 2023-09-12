package com.tapTest.springboot.service;

import com.tapTest.springboot.domain.Program;
import com.tapTest.springboot.domain.Subject;
import com.tapTest.springboot.domain.SubjectEntity;
import com.tapTest.springboot.repository.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceImplTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Test
    void findAll_should_return_subject_list() {
        // Given
        Subject subject = this.buildSubject();
        // When
        when(subjectRepository.findAll()).thenReturn(List.of(subject));
        List<Subject> subjects = this.subjectService.getSubjects();
        // Then
        assertEquals(1, subjects.size());
        verify(this.subjectRepository).findAll();
    }

    @Test
    void findById_should_return_subject() throws Exception {
        // Given
        Subject subject = this.buildSubject();
        // When
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        Subject returnedSubject = this.subjectService.getSubject(1L);
        // Then
        assertEquals(subject.getId(), returnedSubject.getId());
        verify(this.subjectRepository).findById(1L);
    }

    @Test
    void findAll_should_return_subject_list_with_program() {
        // Given
        Subject subject = this.buildSubject();
        subject.setProgram(this.buildProgram());
        // When
        when(subjectRepository.findAll()).thenReturn(List.of(subject));
        List<SubjectEntity> subjects = this.subjectService.getSubjectsForDisplay();
        // Then
        assertEquals(1, subjects.size());
        assertEquals("Science", subjects.get(0).getProgramName());
        verify(this.subjectRepository).findAll();
    }

    @Test
    void findById_should_return_subject_with_program() throws Exception {
        // Given
        Subject subject = this.buildSubject();
        subject.setProgram(this.buildProgram());
        // When
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        SubjectEntity returnedSubject = this.subjectService.getSubjectForDisplay(1L);
        // Then
        assertEquals(subject.getId(), returnedSubject.getId());
        assertEquals("Science", returnedSubject.getProgramName());
        verify(this.subjectRepository).findById(1L);
    }

    @Test
    void save_should_insert_new_subject() throws Exception {
        // Given
        Subject subject = this.buildSubject();
        // When
        this.subjectService.createSubject(subject);
        // Then
        verify(this.subjectRepository).save(subject);
    }

    @Test
    void save_should_update_subject() throws Exception {
        // Given
        Subject subject = this.buildSubject();
        // When
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        // Then
        assertEquals(1, subject.getFileOrder());
        assertEquals("English", subject.getName());
        // When
        Subject returnedSubject = this.subjectService.getSubject(1L);
        returnedSubject.setName("Spanish");
        returnedSubject.setFileOrder(34);
        this.subjectService.updateSubject((long) returnedSubject.getId(),returnedSubject);
        // Then
        assertEquals(34, returnedSubject.getFileOrder());
        assertEquals("Spanish", returnedSubject.getName());
        verify(this.subjectRepository).save(returnedSubject);
    }

    @Test
    void deleteById_should_delete_employee() throws Exception {
        // Given
        Subject subject = this.buildSubject();
        // When
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        // Then
        assertEquals(1, subject.getFileOrder());
        assertEquals("English", subject.getName());

        // When
        this.subjectService.deleteSubject(1L);
        // Then
        verify(this.subjectRepository).deleteById(1L);
    }

    private Subject buildSubject() {
        Subject subject = new Subject();
        subject.setId(1);
        subject.setName("English");
        subject.setFileOrder(1);
        return subject;
    }

    private Program buildProgram() {
        Program program = new Program();
        program.setId(1);
        program.setName("Science");
        program.setProgramKey("s");
        program.setPassingScore(160);
        return program;
    }
}
