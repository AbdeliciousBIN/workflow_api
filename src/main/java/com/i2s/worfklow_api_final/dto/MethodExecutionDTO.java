package com.i2s.worfklow_api_final.dto;

import com.i2s.worfklow_api_final.enums.ExecutionStatus;
import com.i2s.worfklow_api_final.enums.TaskStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MethodExecutionDTO {
    private long id;
    private long taskId;
    private long methodId;

    private ExecutionStatus status ;

    public MethodExecutionDTO() {
    }
}
