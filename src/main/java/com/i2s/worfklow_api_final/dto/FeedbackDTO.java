package com.i2s.worfklow_api_final.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FeedbackDTO {
    private long id;
    private String message;
    private LocalDateTime feedbackDate;
    private long userId;

    public FeedbackDTO() {
    }


}
