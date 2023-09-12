package com.tapTest.springboot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String name;

    @Min(1)
    @Column(unique=true)
    private int fileOrder;

    @ManyToOne(cascade=CascadeType.PERSIST, fetch = FetchType.LAZY, optional = true)
    @JoinColumn (name="program_id",referencedColumnName="id")
    @JsonIgnore
    private Program program;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject )) return false;
        return Objects.equals(id, ((Subject) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
