package com.tapTest.springboot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Programs")
public class Program {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String programKey;

    @Min(1)
    private int passingScore;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "program",
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JsonIgnore
    private Set<Subject> subjectSet = new HashSet<>();

    public void addSubject(Subject subject) {
        subjectSet.add(subject);
        subject.setProgram(this);
    }

    public void removeSubject(Subject subject) {
        subjectSet.remove(subject);
        subject.setProgram(null);
    }

}
