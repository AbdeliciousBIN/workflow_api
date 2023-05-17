package com.i2s.worfklow_api_final.dto;

import com.i2s.worfklow_api_final.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MethodExecutionDTO {
    private long id;
    private long taskId;
    private long methodId;
    private boolean executed;

    public MethodExecutionDTO() {
    }
}
