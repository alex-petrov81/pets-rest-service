package com.backend.tasks.dto;

import com.backend.tasks.model.dict.PetType;
import com.backend.tasks.model.dict.TrackerType;
import com.backend.tasks.validator.NotNullIfAnotherFieldHasValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@NotNullIfAnotherFieldHasValue.List({
        @NotNullIfAnotherFieldHasValue(
                fieldName = "petType",
                fieldValue = "CAT",
                dependFieldName = "lostTracker")
})
public class PetCreateOrUpdateDto {
    @ApiModelProperty(required = true, name = "petType")
    @JsonProperty("petType")
    @NotNull
    private PetType petType;


    @ApiModelProperty(required = true)
    @JsonProperty("trackerType")
    @NotNull
    private TrackerType trackerType;


    @ApiModelProperty(required = true)
    @JsonProperty("ownerId")
    @NotNull
    private Integer ownerId;

    @ApiModelProperty(required = true)
    @JsonProperty("inZone")
    @NotNull
    private Boolean inZone;

    @ApiModelProperty
    @JsonProperty("lostTracker")
    private Boolean lostTracker;
}
