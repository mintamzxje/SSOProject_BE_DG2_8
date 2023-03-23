package com.sso.payload.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Component Object")
public class ComponentDTO {
    @ApiModelProperty(notes = "The unique UUID of the Component")
    private String uuid;
    @ApiModelProperty(notes = "The name of the Component", required = true)
    private String name;
    @ApiModelProperty(notes = "The code of the Component", required = true)
    private String code;
    @ApiModelProperty(notes = "The icon of the Component", required = true)
    private String icon;
}
