package br.com.task_manager.controller.unit;

import br.com.task_manager.api.request.UserRequest;
import br.com.task_manager.api.response.UserResponse;
import br.com.task_manager.controller.UserController;
import br.com.task_manager.model.Role;
import br.com.task_manager.model.UserModel;
import br.com.task_manager.service.impl.UserServiceImpl;
import br.com.task_manager.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserControllerUnitTest {

    private UserModel userModel;
    private static final String USER_EMAIL = "test@example.com";
    private static final String USER_NAME = "Test";

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl userService;

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenInsertUserShouldReturnHttpStatusCreated(){
        UserRequest userRequest = new UserRequest();
        userRequest.setName(USER_NAME);
        userRequest.setEmail(USER_EMAIL);

        when(userService.insertUser(userModel)).thenReturn(userModel);

        ResponseEntity<UserResponse> response = userController.insertUser(userRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    private void startUser() {
        userModel = new UserModel();
        userModel.setEmail(USER_EMAIL);
        userModel.setRole(Role.USER);
    }
}
