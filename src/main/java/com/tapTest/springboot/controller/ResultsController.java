package com.tapTest.springboot.controller;

import com.tapTest.springboot.domain.JsonInput;
import com.tapTest.springboot.domain.JsonOutput;
import com.tapTest.springboot.domain.ParsedInputObject;
import com.tapTest.springboot.service.InputProcessService;
import com.tapTest.springboot.service.ProcessResultService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/results")
public class ResultsController {

    InputProcessService inputService;
    ProcessResultService processResultService;

    public ResultsController(InputProcessService inputService, ProcessResultService processResultService) {
        this.inputService = inputService;
        this.processResultService = processResultService;
    }

    @PostMapping("/passed")
    public JsonOutput processResults(@RequestBody @NotBlank JsonInput tests) {
        try{
            //Remove the first line and validate against the number of lines left
            List<String> lines = tests.getTestResults().lines().collect(Collectors.toList());
            int totalLines = Integer.parseInt(lines.get(0));
            lines.remove(0);

            if(totalLines == 0)
                throw new IOException("The input is not in the correct format");
            if(totalLines != lines.size())
                throw new IOException("The expected number of lines differ from those indicated in the first line");

            ParsedInputObject input = inputService.processInput(lines, totalLines);
            return new JsonOutput(String.valueOf(processResultService.getResults(input)));
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
