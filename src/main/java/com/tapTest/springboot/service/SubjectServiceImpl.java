package com.tapTest.springboot.service;

import com.tapTest.springboot.domain.Subject;
import com.tapTest.springboot.domain.SubjectEntity;
import com.tapTest.springboot.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService {
    SubjectRepository subjectRepository;

    @Override
    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    /**
     *
     * Returns a list of Subjects with the program they belong to as a String
     * instead of the complete program entity to facilitate presentation and avoid
     * data overhead.
     */
    @Override
    public List<SubjectEntity> getSubjectsForDisplay() {
        List<Subject> subjectList = subjectRepository.findAll();
        List<SubjectEntity> subjectEntities = new ArrayList<>();

        for (Subject subject : subjectList){
            SubjectEntity subjectEntity = new SubjectEntity();
            subjectEntity.setId(subject.getId());
            subjectEntity.setName(subject.getName());
            subjectEntity.setFileOrder(subject.getFileOrder());

            if(subject.getProgram() != null)
                subjectEntity.setProgramName(subject.getProgram().getName());

            subjectEntities.add(subjectEntity);
        }

        return subjectEntities;
    }

    /**
     *
     * Returns a Subject with the program they belong to as a String
     * instead of the complete program entity to facilitate presentation.
     */

    @Override
    public SubjectEntity getSubjectForDisplay(Long id) throws Exception {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new Exception("Id Not Found"));

        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setId(subject.getId());
        subjectEntity.setName(subject.getName());
        subjectEntity.setFileOrder(subject.getFileOrder());
        subjectEntity.setProgramName(subject.getProgram().getName());

        return subjectEntity;
    }

    @Override
    public Subject getSubject(Long id) throws Exception {
        return subjectRepository.findById(id).orElseThrow(() -> new Exception("Id Not Found"));
    }

    @Override
    public Subject createSubject(Subject subject) throws Exception {

        Subject newSubject;
        try {
            newSubject = subjectRepository.save(subject);
        }catch (Exception ex){
            throw new Exception("File order must be unique");
        }
        return newSubject;
    }

    @Override
    public Subject updateSubject(Long id, Subject subject) throws Exception {

        Subject currentSubject = subjectRepository.findById(id).orElseThrow(() -> new Exception("Id Not Found"));
        currentSubject.setName(subject.getName());
        currentSubject.setFileOrder(subject.getFileOrder());
        currentSubject = subjectRepository.save(subject);

        return currentSubject;
    }

    @Override
    public List<Subject> getSubjectsNoProgram() {
        return subjectRepository.getSubjectsNoProgram();
    }

    @Override
    public void deleteSubject(Long id) throws Exception {
        if(subjectRepository.findById(id).isEmpty())
            throw new Exception("Id Not Found");

        subjectRepository.deleteById(id);
    }

}
