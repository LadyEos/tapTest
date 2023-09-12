package com.tapTest.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParsedInputObject {

    private int totalLines;
    private List<Examinee> testResults = new ArrayList<>();

    public void addExaminee( Examinee examinee){
        testResults.add(examinee);
    }
}

