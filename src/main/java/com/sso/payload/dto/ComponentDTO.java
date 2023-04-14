package com.sso.payload.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentDTO {

    @Schema(example = "")
    private String uuid;

    @Schema(example = "Component XYZ")
    private String name;

    @Schema(example = "KEO-502")
    private String code;
    @Schema(example = "Only JPG and PNG images are supported")
    private String icon;
}
