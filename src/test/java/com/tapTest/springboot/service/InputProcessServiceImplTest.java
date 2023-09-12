package com.tapTest.springboot.service;

import com.tapTest.springboot.domain.ParsedInputObject;
import com.tapTest.springboot.repository.SubjectRepository;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InputProcessServiceImplTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private InputProcessServiceImpl inputProcessService;

    List<String> subjectList;

    @Test
    void processInput_should_return_input() {
        // Given
        List<String> lines = new ArrayList<>();
        InputStream is = this.getClass().getResourceAsStream("/test1.txt");
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null;) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        subjectList = Stream.of("English", "Math", "Science", "Japanese", "Geography")
                .collect(Collectors.toList());
        // When
        when(subjectRepository.getAllSubjects()).thenReturn(subjectList);
        ParsedInputObject iE = inputProcessService.processInput(lines, 5);
        // Then
        assertEquals(5, iE.getTestResults().size());
        assertEquals(70, iE.getTestResults().get(0).getRecords().get("English"));
    }

    @Test
    void processInput_should_return_input_with_more_subjects_than_grades() {
        // Given
        List<String> lines = new ArrayList<>();
        InputStream is = this.getClass().getResourceAsStream("/test1.txt");
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null;) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        subjectList = Stream.of("English", "Math", "Science", "Japanese", "Geography", "History")
                .collect(Collectors.toList());
        // When
        when(subjectRepository.getAllSubjects()).thenReturn(subjectList);
        ParsedInputObject iE = inputProcessService.processInput(lines, 5);
        // Then
        assertEquals(5, iE.getTestResults().size());
        assertEquals(70, iE.getTestResults().get(0).getRecords().get("English"));
        assertEquals(0, iE.getTestResults().get(0).getRecords().get("History"));
    }

    @Test
    void processInput_should_return_input_with_more_grades_than_subjects() {
        // Given
        List<String> lines = new ArrayList<>();
        InputStream is = this.getClass().getResourceAsStream("/test1-2.txt");
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null;) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        subjectList = Stream.of("English", "Math", "Science", "Japanese")
                .collect(Collectors.toList());
        // When
        when(subjectRepository.getAllSubjects()).thenReturn(subjectList);
        ParsedInputObject iE = inputProcessService.processInput(lines, 5);
        // Then
        assertEquals(5, iE.getTestResults().size());
        assertEquals(70, iE.getTestResults().get(0).getRecords().get("English"));
        assertNull(iE.getTestResults().get(0).getRecords().get("Geography"));
    }
}
