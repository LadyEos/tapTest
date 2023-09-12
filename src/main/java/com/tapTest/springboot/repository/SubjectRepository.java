package com.tapTest.springboot.repository;

import com.tapTest.springboot.domain.Subject;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("select s.name from Subject s ORDER BY s.fileOrder")
    List<String> getAllSubjects();

    @Query("select s from Subject s WHERE s.program= null ORDER BY s.fileOrder")
    List<Subject> getSubjectsNoProgram();

    @Transactional
    @Modifying
    @Query(value = "UPDATE subjects set program_id = null where id =?1", nativeQuery = true)
    void setProgramNull(String id);
}

