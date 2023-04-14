package com.sso.mapper;

import com.sso.payload.dto.ComponentDTO;
import com.sso.model.Component;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ComponentMapper {
    ComponentMapper MAPPER = Mappers.getMapper(ComponentMapper.class);
    Component mapToComponent(ComponentDTO componentDTO);
    ComponentDTO mapToComponentDTO(Component component);
    List<ComponentDTO> mapListToComponentDTO(List<Component> component);
    List<Component> mapListToComponent(List<ComponentDTO> componentDTO);
}
