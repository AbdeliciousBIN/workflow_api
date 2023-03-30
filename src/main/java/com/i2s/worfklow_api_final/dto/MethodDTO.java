package com.i2s.worfklow_api_final.dto;

import com.i2s.worfklow_api_final.model.Method;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MethodDTO {

    private long id;
    private String methodName;
    private List<ParameterDTO> parameters;

    public MethodDTO() {
    }

    public MethodDTO(Method method) {
        this.id = method.getId();
        this.methodName = method.getMethodName();
        this.parameters = method.getParameters().stream().map(ParameterDTO::new).collect(Collectors.toList());
    }

}
