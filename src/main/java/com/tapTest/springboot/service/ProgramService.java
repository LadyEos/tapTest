package com.tapTest.springboot.service;

import com.tapTest.springboot.domain.Program;
import com.tapTest.springboot.domain.Subject;

import java.util.List;
import java.util.Set;

public interface ProgramService {
    List<Program> getPrograms();

    Program getProgram(Long id) throws Exception;

    Program createProgram(Program program);

    Program updateProgram(Long id, Program program) throws Exception;

    void deleteProgram(Long id) throws Exception;

    boolean addSubject(Long id, Long subjectId) throws Exception;

    boolean removeSubject(Long id, Long subjectId) throws Exception;

    Set<Subject> getSubjects(Long id) throws Exception;
}
