package com.tapTest.springboot.controller;

import com.tapTest.springboot.domain.Subject;
import com.tapTest.springboot.domain.SubjectEntity;
import com.tapTest.springboot.service.SubjectService;
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
@RequestMapping("/subjects")
public class SubjectController {

    SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<SubjectEntity> getSubjects() {
        return subjectService.getSubjectsForDisplay();
    }

    @GetMapping("/{id}")
    public SubjectEntity getSubject(@PathVariable @NotBlank @Min(1) Long id) throws Exception {
        return subjectService.getSubjectForDisplay(id);
    }

    @PostMapping("/create")
    public ResponseEntity createSubject(@RequestBody @Valid Subject subject) throws Exception {
        Subject savedSubject = subjectService.createSubject(subject);
        return ResponseEntity.created(new URI("/subjects/" + savedSubject.getId())).body(savedSubject);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateSubject(@PathVariable @NotBlank @Min(1) Long id, @RequestBody @Valid Subject subject) throws Exception {
        Subject currentSubject = subjectService.updateSubject(id, subject);
        return ResponseEntity.ok(currentSubject);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteSubject(@PathVariable @NotBlank @Min(1) Long id) throws Exception {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/noprogram")
    public List<Subject> getSubjectsNoProgram(){
        return subjectService.getSubjectsNoProgram();
    }
}
