package com.tapTest.springboot.service;

import com.tapTest.springboot.domain.Subject;
import com.tapTest.springboot.domain.SubjectEntity;

import java.util.List;

public interface SubjectService {
    List<Subject> getSubjects();

    List<SubjectEntity> getSubjectsForDisplay();

    SubjectEntity getSubjectForDisplay(Long id) throws Exception;

    Subject getSubject(Long id) throws Exception;

    Subject createSubject(Subject subject) throws Exception;

    Subject updateSubject(Long id, Subject subject) throws Exception;

    List<Subject> getSubjectsNoProgram();

    void deleteSubject(Long id) throws Exception;
}
