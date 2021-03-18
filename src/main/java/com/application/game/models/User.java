package com.application.game.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name ="user")
@Data
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private Integer userId;
    @NotNull
    private String userName;
    @NotNull
    @JsonProperty( value = "password", access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Transient
    @JsonProperty( value = "confirmPass", access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPass;
    @Pattern(regexp="(^[6-9]\\d{9}$)",message="invalid mobile number")
    private String mobileNo;

}
