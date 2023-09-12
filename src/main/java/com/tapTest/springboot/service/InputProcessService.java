package com.tapTest.springboot.service;

import com.tapTest.springboot.domain.ParsedInputObject;

import java.util.List;

public interface InputProcessService {
    //TODO exception handling
    ParsedInputObject processInput(List<String> lines, int totalLines);
}
