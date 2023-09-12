package com.tapTest.springboot.repository;

import com.tapTest.springboot.domain.Setting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class SettingRepositoryTest {

    @Autowired
    private SettingRepository settingRepository;

    @Test
    void findAll_should_return_settings_list() {
        // When
        List<Setting> settings = this.settingRepository.findAll();
        // Then
        assertEquals(1, settings.size());
    }

    @Test
    void findById_should_return_setting() {
        // When
        Optional<Setting> settings = this.settingRepository.findById(1L);
        // Then
        assertTrue(settings.isPresent());
    }

    @Test
    void update_existing_settings() {
        // Given
        Setting existingSetting = new Setting();
        existingSetting.setId(1);
        existingSetting.setGlobalPassingScore(300);
        // When
        Setting updatedSetting = this.settingRepository.save(existingSetting);
        // Then
        assertNotNull(updatedSetting);
        assertEquals(300, updatedSetting.getGlobalPassingScore());
    }

}
