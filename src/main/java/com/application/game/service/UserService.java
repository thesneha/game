package com.application.game.service;

import com.application.game.Response.EntityResponse;
import com.application.game.Response.LoginResponse;
import com.application.game.models.User;
import com.application.game.request.LoginUserRequest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface UserService  {

    public User saveUser(User user) throws Exception;

    LoginResponse loginUser(LoginUserRequest loginUserRequest) throws Exception;
    User fetchUserByMobileNo(String mobileNo);


}
