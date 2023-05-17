package com.i2s.worfklow_api_final.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserParameterDTO {
    private String parameterName ;
    private Object parameterValue;
}
