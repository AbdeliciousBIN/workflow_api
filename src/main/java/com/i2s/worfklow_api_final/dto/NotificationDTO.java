package com.i2s.worfklow_api_final.dto;

import com.i2s.worfklow_api_final.model.Task;
import com.i2s.worfklow_api_final.model.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class NotificationDTO {
    private long id;
    private String message;
    private long userId;
    private long taskId;
    private boolean read;

    public NotificationDTO(){}

}
