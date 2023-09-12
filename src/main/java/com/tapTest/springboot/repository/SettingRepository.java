package com.tapTest.springboot.repository;

import com.tapTest.springboot.domain.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {
}
