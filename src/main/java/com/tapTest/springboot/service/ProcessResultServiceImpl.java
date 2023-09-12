package com.tapTest.springboot.service;

import com.tapTest.springboot.domain.*;
import com.tapTest.springboot.repository.ProgramRepository;
import com.tapTest.springboot.repository.SettingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class ProcessResultServiceImpl implements ProcessResultService {

    ProgramRepository programRepository;
    SettingRepository settingRepository;

    @Override
    public int getResults(ParsedInputObject ie){
        Optional<Setting> setting = settingRepository.findById(1L);
        int globalPassingScore = setting.orElseThrow(RuntimeException::new).getGlobalPassingScore();

        int totalPassed =0;
        for(Examinee ex : ie.getTestResults()){

            Program program = programRepository.findByProgramKey(ex.getProgram());
            Set<Subject> subjectSet =  program.getSubjectSet();

            if(passOrFail(ex, globalPassingScore, subjectSet, program.getPassingScore()))
                totalPassed++;
        }

        return totalPassed;
    }

    /**
     *
     * @param ex The object containing the subjects and their scores
     * @param globalPassingScore Minimum Global passing score
     * @param subjects List of subjects belonging to the examinee's program
     * @param programPassingScore Minimum score to pass in that program
     * @return boolean
     *
     * Calculates whether a single examinee passes or not depending on their program
     * and the minimum global passing score
     */
    private boolean passOrFail(Examinee ex, int globalPassingScore, Set<Subject> subjects, int programPassingScore) {

        //Calculates the total sum of all the scores and compares with the global score
        int examineeGlobalPassingScore = ex.getRecords().values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        if (examineeGlobalPassingScore < globalPassingScore)
            return false;

        //Calculates the sum of the scores for the subjects belonging to the examinees program
        int passingScore = 0;
        for (Subject subject : subjects) {
            passingScore += ex.getRecords().get(subject.getName());
        }

        return passingScore >= programPassingScore;
    }
}
