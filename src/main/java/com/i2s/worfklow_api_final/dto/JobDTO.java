package com.i2s.worfklow_api_final.dto;

import com.i2s.worfklow_api_final.model.Job;
import com.i2s.worfklow_api_final.model.User;
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
public class JobDTO {

    private long id;
    private String title;
    private List<Long> usersId;

    public JobDTO() {
    }

//    public JobDTO(Job job) {
//        this.id = job.getId();
//        this.title = job.getTitle();
//        if (!usersId.isEmpty()) job.getUsers().stream().map(User::getId).collect(Collectors.toList());
//    }

}
