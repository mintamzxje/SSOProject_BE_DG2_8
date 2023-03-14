package com.sso.service;

import com.sso.dto.ComponentDTO;

import java.util.List;
import java.util.UUID;

public interface ComponentService {
    List<ComponentDTO> getAllComponent();
    ComponentDTO createComponent(ComponentDTO componentDTO);
    ComponentDTO updateComponent(ComponentDTO componentDTO, String uuid);
    void deleteComponent(UUID uuid);
    ComponentDTO getComponentById(UUID uuid);
}
