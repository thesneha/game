package com.application.game.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserRequest {

    private String password;
    @Pattern(regexp="(^$|[0-9]{10})",message="invalid mobile number")
    private String mobileNo;
}
