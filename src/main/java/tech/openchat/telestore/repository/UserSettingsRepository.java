package tech.openchat.telestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.openchat.telestore.entity.UserSettings;

/**
 * @author vgorin
 * file created on 2020-04-18 14:25
 */

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {
    UserSettings getByUserId(int userId);
}
