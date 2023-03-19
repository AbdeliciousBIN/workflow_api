package com.i2s.worfklow_api_final.dto;

import com.i2s.worfklow_api_final.model.Job;
import com.i2s.worfklow_api_final.model.User;

import java.util.List;
import java.util.Objects;

public class JobDTO {

    private long id;
    private String title;
    private List<User> users;

    public JobDTO() {
    }
    public JobDTO(Job job){
        this.id= job.getId();
        this.title=job.getTitle();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobDTO jobDTO = (JobDTO) o;
        return getId() == jobDTO.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "JobDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", users=" + users +
                '}';
    }
}
