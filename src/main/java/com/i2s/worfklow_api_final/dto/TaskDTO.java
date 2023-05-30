    package com.i2s.worfklow_api_final.dto;

    import com.i2s.worfklow_api_final.enums.TaskStatus;
    import com.i2s.worfklow_api_final.model.Task;
    import com.i2s.worfklow_api_final.model.File;
    import com.i2s.worfklow_api_final.model.Method;
    import lombok.EqualsAndHashCode;
    import lombok.Getter;
    import lombok.Setter;
    import lombok.ToString;

    import java.time.LocalDateTime;
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
        private String instructions;
        private boolean requiredVerification;
        private long stepId;
        private List<Long> fileIds;
        private List<Long> methodIds;
        private List<Long> assignedJobIds;
        private List<Long> parentTaskIds;
        private List<Long> childTaskIds;
        private List<MethodExecutionDTO> methodExecutions;

        private TaskStatus status;
        private List<FeedbackDTO> feedbacks;
        private LocalDateTime createdAt;
        private LocalDateTime startedAt;
        private LocalDateTime finishedAt;

        public TaskDTO() {
        }


    }
