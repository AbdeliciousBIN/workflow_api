package com.i2s.worfklow_api_final.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "method_executions")
public class MethodExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "method_id")
    private Method method;


    @Column(nullable = false)
    private boolean executed;

    public MethodExecution() {
    }
}
