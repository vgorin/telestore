package tech.openchat.telestore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.openchat.telestore.entity.ServiceArea;
import tech.openchat.telestore.repository.ServiceAreaRepository;

import java.util.List;

/**
 * @author vgorin
 * file created on 2020-04-14 20:46
 */

@Service
@RequiredArgsConstructor
public class ServiceAreaService {
    private final ServiceAreaRepository serviceAreaRepository;

    public List<ServiceArea> listServiceAreas() {
        return serviceAreaRepository.findAll();
    }
}
