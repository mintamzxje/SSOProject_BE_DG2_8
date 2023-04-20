package com.sso.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentDTORequest {
    @Schema(example = "Component XYZ")
    private String name;
    @Schema(example = "KEO-502")
    private String code;
}
