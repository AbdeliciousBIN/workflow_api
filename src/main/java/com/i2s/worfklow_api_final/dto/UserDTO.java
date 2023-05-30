package com.i2s.worfklow_api_final.dto;


import com.i2s.worfklow_api_final.model.User;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDTO {

    private long id;
    private String fullName;
    private String email;
    private String role;

    private String passwordHash;
    private String phoneNumber;
    private String address;
    private String profilePicture;
    private long jobId;

    public UserDTO() {
    }


}
