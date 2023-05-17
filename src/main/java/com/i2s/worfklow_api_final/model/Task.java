package com.i2s.worfklow_api_final.model;


import com.i2s.worfklow_api_final.enums.TaskStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(nullable = false, name = "task_name")
    private String taskName;

    private String description;
    private String instructions;
    private boolean requiredVerification;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id")
    private Step step;

    @ManyToMany
    @JoinTable(name = "task_file", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
    private List<File> files = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "task_method", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "method_id"))
    private List<Method> methods = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "task_job", joinColumns = @JoinColumn(name="task_id"), inverseJoinColumns = @JoinColumn(name ="job_id"))
    private List<Job> assignedJobs = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "task_parent", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "parent_id"))
    private List<Task> parentTasks = new ArrayList<>();

    @ManyToMany(mappedBy = "parentTasks")
    private List<Task> childTasks = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MethodExecution> methodExecutions = new ArrayList<>();
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public Task() {

    }
}
