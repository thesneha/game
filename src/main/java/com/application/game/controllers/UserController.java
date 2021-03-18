package com.application.game.controllers;


import com.application.game.Response.EntityResponse;
import com.application.game.Response.LoginResponse;
import com.application.game.models.User;
import com.application.game.request.LoginUserRequest;
import com.application.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/gameApplication")
public class UserController {

    @Autowired
    private UserService userService;

//    @RequestMapping(value ="/registerUser", method = RequestMethod.POST)
//    public ResponseEntity<EntityResponse> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) throws Exception {
//        if (bindingResult.hasErrors()) {
//            String error = bindingResult.getFieldError().getDefaultMessage();
//            return new ResponseEntity<>(new EntityResponse("4000", error), HttpStatus.BAD_REQUEST);
//        }
//
//          user= userService.saveUser(user);
//        return new ResponseEntity<>(new EntityResponse("4000", user,"User is created"), HttpStatus.OK);
//    }
    @RequestMapping(value ="/registerUser", method = RequestMethod.POST)
    public ResponseEntity<EntityResponse> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getFieldError().getDefaultMessage();
            //return new ResponseEntity<>(new EntityResponse("4000", error), HttpStatus.BAD_REQUEST);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new EntityResponse(false,error));

        }

        user= userService.saveUser(user);
       // return new ResponseEntity<>(new EntityResponse("4000", user,"User is created"), HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new EntityResponse(true,"user is created", user));
    }

    @RequestMapping(value ="/login", method = RequestMethod.POST)
    public ResponseEntity<EntityResponse>  loginUser( @Valid @RequestBody LoginUserRequest loginUserRequest, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            String error = bindingResult.getFieldError().getDefaultMessage();
           // return new ResponseEntity<>(new EntityResponse("4000", error), HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new EntityResponse(false,error));
        }
        LoginResponse response = userService.loginUser(loginUserRequest);
        //return new ResponseEntity<>(new EntityResponse("4000", response," user login"), HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new EntityResponse(true,"user login", response));

    }





    }

