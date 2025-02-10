package com.backend.tasks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;

@EqualsAndHashCode(callSuper = true)
@Data
public class PetReadDto extends PetCreateOrUpdateDto {
    @ApiModelProperty(value = "Pet ID")
    @Valid
    @JsonProperty("id")
    private Long id;
}
