package com.i2s.worfklow_api_final.dto;


import com.i2s.worfklow_api_final.model.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDTO {

    private long id;
    private String fullName;
    private String email;
    private String roleName;
    private String passwordHash;
    private String phoneNumber;
    private String address;
    private long profilePictureId;
    private long jobId;

    public UserDTO() {
    }


}
