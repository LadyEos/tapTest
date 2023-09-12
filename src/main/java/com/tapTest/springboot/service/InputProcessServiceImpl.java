package com.tapTest.springboot.service;

import com.tapTest.springboot.domain.Examinee;
import com.tapTest.springboot.domain.ParsedInputObject;
import com.tapTest.springboot.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toCollection;

@AllArgsConstructor
@Service
public class InputProcessServiceImpl implements InputProcessService {

    SubjectRepository subjectRepository;

    /**
     *
     * ParsedInputObject contains the number of lines present in the Input string,
     * and a list for each line and the corresponding subjects each score belongs to.
     * As possible extension in the future, this will facilitate the presentation of data
     * and retrieval of each subject's score.
     */
    @Override
    public ParsedInputObject processInput(List<String> lines, int totalLines){

        ParsedInputObject input = new ParsedInputObject();
        input.setTotalLines(totalLines);

        //creates a list of all the subjects names in the order marked by the fileOrder column
        List<String> subjects = subjectRepository.getAllSubjects();

        //Splits each score and creates a list. Removes the program the examinee belongs to and stores separately
        for (String line: lines ){
            List<String> examineeList = Stream.of(line.split(" "))
                    .map(String::trim)
                    .collect(Collectors.toList());
            String program = examineeList.get(0);
            examineeList.remove(0);

            List<Integer> examineeGradeList = new ArrayList<>();
            try {
                examineeGradeList = examineeList.stream().map(Integer::parseInt).collect(toCollection(ArrayList::new));
            }catch (NumberFormatException ex){
                throw new NumberFormatException("Please input only numbers for each score");
            }

            //makes both lists the same size
            if(subjects.size() > examineeGradeList.size()){
                //fill with 0 the missing scores
                int missing = subjects.size() - examineeGradeList.size();
                for (int i = 0; i < missing; i++){
                    examineeGradeList.add(0);
                }
            }else if(subjects.size() < examineeGradeList.size()){
                //ignore the extra scores that do not have a subject
                while (examineeGradeList.size() != subjects.size()){
                    examineeGradeList.remove(examineeGradeList.size()-1);
                }
            }

            Map<String, Integer> examineeGradeMap =  IntStream.range(0, subjects.size()).boxed()
                    .collect(Collectors.toMap(subjects::get, examineeGradeList::get));

            input.addExaminee(new Examinee(program, examineeGradeMap));
        }

        return input;
    }
}
