package com.tapTest.springboot.controller;

import com.tapTest.springboot.domain.Setting;
import com.tapTest.springboot.service.SettingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/settings")
public class SettingController {

    SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping
    public List<Setting> getSettings() {
        return settingService.getSettings();
    }

    @GetMapping("/{id}")
    public Setting getSetting(@PathVariable @NotBlank @Min(1) Long id) throws Exception {
        return settingService.getSetting(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateSetting(@PathVariable @NotBlank @Min(1) Long id, @RequestBody @Valid Setting setting) throws Exception {
        Setting currentSetting = settingService.updateSetting(id, setting);
        return ResponseEntity.ok(currentSetting);
    }
}
