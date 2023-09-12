package com.tapTest.springboot.service;

import com.tapTest.springboot.domain.Program;
import com.tapTest.springboot.domain.Subject;
import com.tapTest.springboot.repository.ProgramRepository;
import com.tapTest.springboot.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class ProgramServiceImpl implements ProgramService {
    SubjectRepository subjectRepository;
    ProgramRepository programRepository;

    @Override
    public List<Program> getPrograms() {
        return programRepository.findAll();
    }

    @Override
    public Program getProgram(Long id) throws Exception {
        return programRepository.findById(id).orElseThrow(() -> new Exception("Id Not Found"));
    }

    @Override
    public Program createProgram(Program program){
        return programRepository.save(program);
    }

    @Override
    public Program updateProgram(Long id, Program program) throws Exception {

        Program currentProgram = programRepository.findById(id).orElseThrow(() -> new Exception("Id Not Found"));
        currentProgram.setName(program.getName());
        currentProgram.setProgramKey(program.getProgramKey());
        currentProgram.setPassingScore(program.getPassingScore());
        currentProgram = programRepository.save(program);

        return currentProgram;
    }

    @Override
    public void deleteProgram(Long id) throws Exception {
        if(programRepository.findById(id).isEmpty())
            throw new Exception("Id Not Found");

        programRepository.deleteById(id);
    }

    /**
     *
     * @param id Long Program id
     * @param subjectId Long Subject id
     * @return boolean
     *
     * Adds a subject to a program. To facilitate the creation of a Subject, and due to JPA oneToMany,
     * it was decided that the subjects will be added to a program after their creation.
     */
    @Override
    public boolean addSubject(Long id, Long subjectId) throws Exception {

        Program program = programRepository.findById(id).orElseThrow(() -> new Exception("Id Not Found"));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new Exception("Id Not Found"));

        program.addSubject(subject);
        programRepository.save(program);

        return true;
    }

    /**
     *
     * @param id Long Program id
     * @param subjectId Long Subject id
     * @return boolean
     *
     * Removes a subject from a program.
     * This method will call a query that sets the fk of subject as NULL,
     * since there are not currently a method in JPA to remove an entity without deleting it.
     */
    @Override
    public boolean removeSubject(Long id, Long subjectId) throws Exception {

        programRepository.findById(id).orElseThrow(() -> new Exception("Id Not Found"));
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new Exception("Id Not Found"));

        subjectRepository.setProgramNull(subjectId.toString());
        subjectRepository.save(subject);

        return true;
    }

    @Override
    public Set<Subject> getSubjects(Long id) throws Exception {
        Program program = programRepository.findById(id).orElseThrow(() -> new Exception("Id Not Found"));
        return program.getSubjectSet();
    }
}
