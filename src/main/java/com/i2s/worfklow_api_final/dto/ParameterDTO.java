package com.i2s.worfklow_api_final.dto;

import com.i2s.worfklow_api_final.model.Parameter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ParameterDTO {
    private long id;
    private String parameterName;
    private String parameterType;

    public ParameterDTO() {
    }

    public ParameterDTO(Parameter parameter) {
        this.id = parameter.getId();
        this.parameterName = parameter.getParameterName();
        this.parameterType = parameter.getParameterType();
    }
}
