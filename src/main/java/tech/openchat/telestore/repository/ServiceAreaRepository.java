package tech.openchat.telestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.openchat.telestore.entity.ServiceArea;

/**
 * @author vgorin
 * file created on 2020-04-14 20:46
 */

@Repository
public interface ServiceAreaRepository extends JpaRepository<ServiceArea, Long> {
}
