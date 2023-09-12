package com.tapTest.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Settings")
public class Setting {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Min(1)
    private int globalPassingScore;
}
