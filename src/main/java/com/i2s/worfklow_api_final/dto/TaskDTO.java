package com.i2s.worfklow_api_final.dto;

import com.i2s.worfklow_api_final.model.Task;
import com.i2s.worfklow_api_final.model.File;
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
public class TaskDTO {
    private long id;
    private String taskName;
    private String description;
    private long stepId;
    private List<Long> fileIds;
    private List<Long> methodIds;
    private List<Long> parentTaskIds;
    private List<Long> childTaskIds;

    public TaskDTO() {
    }

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.taskName = task.getTaskName();
        this.description = task.getDescription();
        this.stepId = task.getStep() != null ? task.getStep().getId() : null;
        this.fileIds = task.getFiles().stream().map(File::getId).collect(Collectors.toList());
        this.methodIds = task.getMethods().stream().map(Method::getId).collect(Collectors.toList());
        this.parentTaskIds = task.getParentTasks().stream().map(Task::getId).collect(Collectors.toList());
        this.childTaskIds = task.getChildTasks().stream().map(Task::getId).collect(Collectors.toList());
    }
}
