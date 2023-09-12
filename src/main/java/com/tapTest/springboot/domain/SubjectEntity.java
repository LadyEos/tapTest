package com.tapTest.springboot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class SubjectEntity {

    private int id;

    @NotEmpty
    private String name;

    @Min(1)
    @Column(unique=true)
    private int fileOrder;

    private String programName;
}
