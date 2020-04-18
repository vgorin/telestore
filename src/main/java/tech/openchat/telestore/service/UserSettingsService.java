package tech.openchat.telestore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.openchat.telestore.entity.ServiceArea;
import tech.openchat.telestore.entity.UserSettings;
import tech.openchat.telestore.repository.ServiceAreaRepository;
import tech.openchat.telestore.repository.UserSettingsRepository;

import javax.persistence.EntityNotFoundException;

/**
 * @author vgorin
 * file created on 2020-04-18 14:24
 */

@Service
@RequiredArgsConstructor
public class UserSettingsService {
    private final UserSettingsRepository userSettingsRepository;
    private final ServiceAreaRepository serviceAreaRepository;

    public UserSettings getUserSettings(int userId) {
        return userSettingsRepository.getByUserId(userId);
    }

    public UserSettings setServiceArea(int userId, long serviceAreaId) {
        ServiceArea serviceArea = serviceAreaRepository.findById(serviceAreaId).orElseThrow(EntityNotFoundException::new);

        UserSettings userSettings = userSettingsRepository.getByUserId(userId);
        if(userSettings == null) {
            userSettings = new UserSettings();
            userSettings.setUserId(userId);
        }

        userSettings.setServiceArea(serviceArea);
        return userSettingsRepository.save(userSettings);
    }
}
