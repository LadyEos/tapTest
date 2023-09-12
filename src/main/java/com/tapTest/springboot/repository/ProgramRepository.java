package com.tapTest.springboot.repository;

import com.tapTest.springboot.domain.Program;
import com.tapTest.springboot.domain.Subject;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    Program findByProgramKey(String programKey);
}
