package com.sso.SSO_BE_DG2_8.service;

import com.sso.SSO_BE_DG2_8.dto.ComponentDTO;

import java.util.List;
import java.util.UUID;

public interface ComponentService {
    List<ComponentDTO> getAllComponent();
    ComponentDTO createComponent(ComponentDTO componentDTO);
    ComponentDTO updateComponent(ComponentDTO componentDTO, UUID uuid);
    void deleteComponent(UUID uuid);
    ComponentDTO getComponentById(UUID uuid);
}
