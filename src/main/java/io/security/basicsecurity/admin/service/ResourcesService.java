package io.security.basicsecurity.admin.service;

import io.security.basicsecurity.security.entity.Resources;
import io.security.basicsecurity.security.repository.ResourcesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @since       2022.04.11
 * @author      minam
 * @description resources service
 **********************************************************************************************************************/
@Service
@RequiredArgsConstructor
public class ResourcesService {

    private final ResourcesRepository resourcesRepository;

    @Transactional
    public Resources selectResources(long id) {
        return resourcesRepository.findById(id).orElse(new Resources());
    }

    @Transactional
    public List<Resources> selectResources() {
        return resourcesRepository.findAll();
    }

    @Transactional
    public void insertResources(Resources resources){
        resourcesRepository.save(resources);
    }

    @Transactional
    public void deleteResources(Resources resource) {
        resourcesRepository.delete(resource);
    }
}