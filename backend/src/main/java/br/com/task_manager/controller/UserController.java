package br.com.task_manager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.task_manager.api.UserApi;
import br.com.task_manager.api.request.UserRequest;
import br.com.task_manager.api.response.UserResponse;
import br.com.task_manager.mapper.UserMapper;
import br.com.task_manager.model.UserModel;
import br.com.task_manager.service.UserService;

@RestController
public class UserController implements UserApi{
    
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserResponse> insertUser(UserRequest userRequest) {
        System.out.println(userRequest.getName());
        System.out.println(userRequest.getPassword());
        System.out.println(userRequest.getEmail());
        UserModel user = UserMapper.INSTANCE.userRequestToUserModel(userRequest);
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        System.out.println(user.getEmail());
        user = userService.insertUser(user);
        return ResponseEntity.status(201).body(UserMapper.INSTANCE.userModelToUserResponse(user));
    }
}
