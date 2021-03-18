package com.application.game.service;

import com.application.game.Response.*;
import com.application.game.models.User;
import com.application.game.repository.UserRepository;
import com.application.game.request.LoginUserRequest;
import com.application.game.utils.EncodeUtil;
import com.application.game.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.IOException;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  UserService userService;

    @Override
    public User saveUser(User user) throws Exception {
        String tempMobileNo= user.getMobileNo();
        if (tempMobileNo!=null&&!"".equals(tempMobileNo))
        {
            User userObj= userService.fetchUserByMobileNo(tempMobileNo);
            if (userObj!=null)
            {
                throw new Exception("user is already present");
            }
        }
        if (!(user.getPassword().equals(user.getConfirmPass())))
        {
            throw new Exception("password is not equal to confirm password");
        }
         user.setPassword( EncodeUtil.encrypt(user.getPassword(),JsonUtil.secretKey));
         user=userRepository.save(user);
        return user;
    }
    
    @Override
    public User fetchUserByMobileNo(String mobileNo)
    {
        return userRepository.findByMobileNo(mobileNo) ;// userRepository.findByEmailId(emailId);
    }

    @Override
    public LoginResponse loginUser( LoginUserRequest loginUserRequest) throws Exception {
        User user=fetchUserByMobileNo(loginUserRequest.getMobileNo());
        if (user==null)
        {
            throw new Exception("User is not register");
        }
        if (!loginUserRequest.getPassword().equals(EncodeUtil.decrypt(user.getPassword(),JsonUtil.secretKey)))
        {
            throw new Exception("password is incorrect");
        }

      UserToken userToken = UserToken.from(user);
      String response= JsonUtil.getJsonStringFromObject(userToken);

    return LoginResponse.builder()
              .userResponse(UserResponse.from(user))
              .tokenResponse(TokenResponse.builder()
                      .accessToken(EncodeUtil.encrypt(response,JsonUtil.secretKey))
                      .expiresIn(30*60)
                      .build())
              .build();
    }
}
