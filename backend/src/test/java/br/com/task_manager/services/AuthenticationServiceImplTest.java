package br.com.task_manager.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.com.task_manager.api.request.OauthRequest;
import br.com.task_manager.api.response.OauthResponse;
import br.com.task_manager.model.UserModel;
import br.com.task_manager.service.JwtService;
import br.com.task_manager.service.UserService;
import br.com.task_manager.service.impl.AuthenticationServiceImpl;

class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    private OauthRequest oauthRequest;
    private UserModel userModel;
    private static final String JWT_TOKEN = "test-jwt-token";

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        startOauthRequest();
        startUserModel();
    }

    @Test
    void whenOauthReturnsOauthResponse() {
        when(userService.getUserByLogin(oauthRequest.getEmail())).thenReturn(userModel);
        when(jwtService.generateToken(userModel)).thenReturn(JWT_TOKEN);

        OauthResponse response = authenticationService.oauth(oauthRequest);

        assertNotNull(response);
        assertEquals(OauthResponse.class, response.getClass());
        assertEquals(JWT_TOKEN, response.getAccessToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService, times(1)).getUserByLogin(oauthRequest.getEmail());
        verify(jwtService, times(1)).generateToken(userModel);
    }

    @Test
    void whenAuthenticationFailsThrowsException() {
        doThrow(new BadCredentialsException("Bad credentials")).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(BadCredentialsException.class, () -> authenticationService.oauth(oauthRequest));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService, never()).getUserByLogin(anyString());
        verify(jwtService, never()).generateToken(any());
    }

    private void startOauthRequest() {
        oauthRequest = new OauthRequest();
        oauthRequest.setEmail("test@example.com");
        oauthRequest.setPassword("password123");
    }

    private void startUserModel() {
        userModel = new UserModel();
        userModel.setEmail("test@example.com");
        userModel.setPassword("password123");
    }
}
