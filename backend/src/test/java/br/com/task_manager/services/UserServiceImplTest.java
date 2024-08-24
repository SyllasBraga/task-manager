package br.com.task_manager.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.task_manager.exception.TaskManagerBadRequestException;
import br.com.task_manager.model.Role;
import br.com.task_manager.model.UserModel;
import br.com.task_manager.repository.UserRepository;
import br.com.task_manager.service.impl.UserServiceImpl;

class UserServiceImplTest {

    private UserModel userModel;
    private static final String USER_EMAIL = "test@example.com";
    private static final String USER_PASSWORD = "password123";

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenGetUserByLoginReturnsUser() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(userModel);

        UserModel result = userService.getUserByLogin(USER_EMAIL);

        assertNotNull(result);
        assertEquals(UserModel.class, result.getClass());
        assertEquals(USER_EMAIL, result.getEmail());
    }

    @Test
    void whenInsertUserThrowsException() {
        when(userRepository.save(userModel)).thenThrow(TaskManagerBadRequestException.class);

        assertThrows(TaskManagerBadRequestException.class, () -> {
            userService.insertUser(userModel);  
        });
    }

    @Test
    void whenInsertUserReturnsUser() {
        when(passwordEncoder.encode(USER_PASSWORD)).thenReturn("encodedPassword123");
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        UserModel result = userService.insertUser(userModel);

        assertNotNull(result);
        assertEquals(UserModel.class, result.getClass());
        assertEquals(Role.USER, result.getRole());
        verify(passwordEncoder, times(1)).encode(USER_PASSWORD);
        verify(userRepository, times(1)).save(userModel);
    }

    @Test
    void whenUserDetailsServiceReturnsUserDetails() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(userModel);

        UserDetailsService userDetailsService = userService.userDetailsService();
        UserDetails userDetails = userDetailsService.loadUserByUsername(USER_EMAIL);

        assertNotNull(userDetails);
        assertEquals(UserModel.class, userDetails.getClass());
        assertEquals(USER_EMAIL, userDetails.getUsername());
    }

    @Test
    void whenLoadUserByUsernameThrowsException() {
        when(userRepository.findByEmail(USER_EMAIL)).thenThrow(UsernameNotFoundException.class);

        UserDetailsService userDetailsService = userService.userDetailsService();

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(USER_EMAIL);
        });
    }

    private void startUser() {
        userModel = new UserModel();
        userModel.setEmail(USER_EMAIL);
        userModel.setPassword(USER_PASSWORD);
        userModel.setRole(Role.USER);
    }
}

