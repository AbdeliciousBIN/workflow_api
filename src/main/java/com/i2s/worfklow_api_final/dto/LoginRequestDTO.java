package com.i2s.worfklow_api_final.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequestDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String password;


}
