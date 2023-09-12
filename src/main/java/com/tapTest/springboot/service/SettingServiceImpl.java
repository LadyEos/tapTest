package com.tapTest.springboot.service;

import com.tapTest.springboot.domain.Setting;
import com.tapTest.springboot.repository.SettingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SettingServiceImpl implements SettingService {
    SettingRepository settingRepository;

    @Override
    public List<Setting> getSettings() {
        return settingRepository.findAll();
    }

    @Override
    public Setting getSetting(Long id) throws Exception {
        return settingRepository.findById(id).orElseThrow(() -> new Exception("Id Not Found"));
    }

    @Override
    public Setting updateSetting(Long id, Setting setting) throws Exception {

        Setting currentSetting = settingRepository.findById(id).orElseThrow(() -> new Exception("Id Not Found"));
        currentSetting.setGlobalPassingScore(setting.getGlobalPassingScore());
        currentSetting = settingRepository.save(setting);

        return currentSetting;
    }

}
