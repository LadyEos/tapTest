package com.tapTest.springboot.service;

import com.tapTest.springboot.domain.*;
import com.tapTest.springboot.repository.ProgramRepository;
import com.tapTest.springboot.repository.SettingRepository;
import com.tapTest.springboot.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProcessResultServiceImplTest {

    @Mock
    ProgramRepository programRepository;
    @Mock
    SettingRepository settingRepository;
    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private ProcessResultServiceImpl processResultService;
    @InjectMocks
    private InputProcessServiceImpl inputProcessService;

    Subject subject1, subject2, subject3, subject4, subject5;

    Program program1, program2;

    @BeforeEach
    public void init() {
        subject1 = buildSubject(1, "English", 1);
        subject2 = buildSubject(2, "Math", 2);
        subject3 = buildSubject(3, "Science", 3);
        subject4 = buildSubject(4, "Japanese", 4);
        subject5 = buildSubject(5, "Geography", 5);

        program1 = buildProgram(1, "Science", "s", 160);
        program2 = buildProgram(2, "Humanities", "1", 160);

        program1.addSubject(subject2);
        program1.addSubject(subject3);

        program2.addSubject(subject4);
        program2.addSubject(subject5);
    }

    @Test
    void getResults_should_return_passed() {
        // Given
        List<String> subjectList = Stream.of("English", "Math", "Science", "Japanese", "Geography")
                .collect(Collectors.toList());

        ParsedInputObject ie = buildInputEntity("/test1.txt", subjectList);
        Setting setting = buildSetting(350);
        when(settingRepository.findById(1L)).thenReturn(Optional.of(setting));

        // When
        when(programRepository.findByProgramKey("s")).thenReturn(program1);
        when(programRepository.findByProgramKey("l")).thenReturn(program2);

        int result = processResultService.getResults(ie);
        assertEquals(2, result);
    }

    @Test
    void getResults_should_return_passed2() {
        // Given
        List<String> subjectList = Stream.of("English", "Math", "Science", "Japanese", "Geography")
                .collect(Collectors.toList());

        ParsedInputObject ie = buildInputEntity("/test2-1.txt", subjectList);
        Setting setting = buildSetting(350);
        when(settingRepository.findById(1L)).thenReturn(Optional.of(setting));

        // When
        when(programRepository.findByProgramKey("s")).thenReturn(program1);
        when(programRepository.findByProgramKey("l")).thenReturn(program2);

        int result = processResultService.getResults(ie);
        assertEquals(3, result);
    }

    @Test
    void getResults_more_programs_subjects() {
        // Given
        List<String> subjectList = Stream.of("English", "Math", "Science", "Japanese", "Geography", "Creative Writing")
                .collect(Collectors.toList());

        ParsedInputObject ie = buildInputEntity("/test2-2.txt", subjectList);
        Setting setting = buildSetting(350);
        when(settingRepository.findById(1L)).thenReturn(Optional.of(setting));

        Subject subject6 = buildSubject(5, "Creative Writing", 6);
        Program program3 = buildProgram(3, "Literature", "i", 150);

        program3.addSubject(subject1);
        program3.addSubject(subject6);

        // When
        when(programRepository.findByProgramKey("s")).thenReturn(program1);
        when(programRepository.findByProgramKey("l")).thenReturn(program2);
        when(programRepository.findByProgramKey("i")).thenReturn(program3);

        int result = processResultService.getResults(ie);
        assertEquals(3, result);
    }

    @Test
    void getResults_should_return_passed_lower_score() {
        // Given
        List<String> subjectList = Stream.of("English", "Math", "Science", "Japanese", "Geography")
                .collect(Collectors.toList());

        ParsedInputObject ie = buildInputEntity("/test2-1.txt", subjectList);
        Setting setting = buildSetting(330);
        when(settingRepository.findById(1L)).thenReturn(Optional.of(setting));

        program1.setPassingScore(130);
        program2.setPassingScore(120);

        // When
        when(programRepository.findByProgramKey("s")).thenReturn(program1);
        when(programRepository.findByProgramKey("l")).thenReturn(program2);

        int result = processResultService.getResults(ie);
        assertEquals(3, result);
    }

    private ParsedInputObject buildInputEntity(String testFile, List<String> subjectList) {
        List<String> lines = new ArrayList<>();
        InputStream is = this.getClass().getResourceAsStream(testFile);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null;) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // When
        when(subjectRepository.getAllSubjects()).thenReturn(subjectList);
        ParsedInputObject iE = inputProcessService.processInput(lines, lines.size());

        return iE;
    }

    private Setting buildSetting(int score) {
        Setting setting = new Setting();
        setting.setId(1);
        setting.setGlobalPassingScore(score);
        return setting;
    }

    private Program buildProgram(int id, String name, String programKey, int passingScore) {
        Program program = new Program();
        program.setId(id);
        program.setName(name);
        program.setProgramKey(programKey);
        program.setPassingScore(passingScore);
        return program;
    }

    private Subject buildSubject(int id, String name, int fileOrder) {
        Subject subject = new Subject();
        subject.setId(id);
        subject.setName(name);
        subject.setFileOrder(fileOrder);
        return subject;
    }

}
