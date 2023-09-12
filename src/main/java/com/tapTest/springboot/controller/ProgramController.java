package com.tapTest.springboot.controller;

import com.tapTest.springboot.domain.Program;
import com.tapTest.springboot.service.ProgramService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/programs")
public class ProgramController {

    ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping
    public List<Program> getPrograms() {
        return programService.getPrograms();
    }

    @GetMapping("/{id}")
    public Program getProgram(@PathVariable @NotBlank @Min(1) Long id) throws Exception {
        return programService.getProgram(id);
    }

    @PostMapping("/create")
    public ResponseEntity createProgram(@RequestBody @Valid Program program) throws URISyntaxException {
        Program savedProgram = programService.createProgram(program);
        return ResponseEntity.created(new URI("/programs/" + savedProgram.getId())).body(savedProgram);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProgram(@PathVariable @NotBlank @Min(1) Long id, @RequestBody @Valid Program program) throws Exception {
        Program currentProgram = programService.updateProgram(id,program);
        return ResponseEntity.ok(currentProgram);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProgram(@PathVariable @NotBlank @Min(1) Long id) throws Exception {
        programService.deleteProgram(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/addSubject/{id}/{subjectId}")
    public ResponseEntity addSubject(@PathVariable @NotBlank @Min(1) Long id, @PathVariable @NotBlank @Min(1) Long subjectId) throws Exception {
        if(!programService.addSubject(id, subjectId))
            throw new Exception("Could not add subject");

        return ResponseEntity.ok("Subject added successfully");
    }

    @GetMapping("/deleteSubject/{id}/{subjectId}")
    public ResponseEntity deleteSubject(@PathVariable @NotBlank @Min(1) Long id, @PathVariable @NotBlank @Min(1) Long subjectId) throws Exception {
        if(!programService.removeSubject(id, subjectId))
            throw new Exception("Could not remove subject");

        return ResponseEntity.ok("Subject removed successfully");
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity getSubject(@PathVariable @NotBlank @Min(1) Long id) throws Exception {
        return ResponseEntity.ok(programService.getSubjects(id));
    }
}
