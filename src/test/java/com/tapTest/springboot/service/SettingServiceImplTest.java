package com.tapTest.springboot.service;

import com.tapTest.springboot.domain.Setting;
import com.tapTest.springboot.repository.SettingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SettingServiceImplTest {

    @Mock
    private SettingRepository settingRepository;;

    @InjectMocks
    private SettingServiceImpl settingService;

    @Test
    void findAll_should_return_setting_list() {
        // Given
        Setting setting = this.buildTestSetting();
        // When
        when(settingRepository.findAll()).thenReturn(List.of(setting));
        List<Setting> settings = this.settingService.getSettings();
        // Then
        assertEquals(1, settings.size());
        verify(this.settingRepository).findAll();
    }

    @Test
    void findById_should_return_setting() throws Exception {
        // Given
        Setting setting = this.buildTestSetting();
        // When
        when(settingRepository.findById(1L)).thenReturn(Optional.of(setting));
        Setting returnedSetting = this.settingService.getSetting(1L);
        // Then
        assertEquals(setting.getId(), returnedSetting.getId());
        verify(this.settingRepository).findById(1L);
    }

    @Test
    void save_should_update_setting() throws Exception {
        // Given
        Setting setting = this.buildTestSetting();
        // When
        when(settingRepository.findById(1L)).thenReturn(Optional.of(setting));
        // Then
        assertEquals(1, setting.getId());
        assertEquals(350, setting.getGlobalPassingScore());
        // When
        Setting returnedSetting = this.settingService.getSetting(1L);
        returnedSetting.setGlobalPassingScore(300);
        this.settingService.updateSetting((long) returnedSetting.getId(),returnedSetting);
        // Then
        assertEquals(300, setting.getGlobalPassingScore());
        verify(this.settingRepository).save(setting);
    }

    private Setting buildTestSetting() {
        Setting setting = new Setting();
        setting.setId(1);
        setting.setGlobalPassingScore(350);
        return setting;
    }
}
