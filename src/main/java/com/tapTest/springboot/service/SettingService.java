package com.tapTest.springboot.service;

import com.tapTest.springboot.domain.Setting;

import java.util.List;

public interface SettingService {
    List<Setting> getSettings();
    Setting getSetting(Long id) throws Exception;
    Setting updateSetting(Long id, Setting setting) throws Exception;
}
