package br.com.task_manager.controller.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.task_manager.api.request.OauthRequest;
import br.com.task_manager.api.response.OauthResponse;
import br.com.task_manager.controller.AuthenticationController;
import br.com.task_manager.service.AuthenticationService;

class AuthenticationControllerUnitTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;

    private OauthRequest oauthRequest;
    private OauthResponse oauthResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startOauth();
    }

    @Test
    void whenOauthShouldReturnHttpStatusOk() {
        when(authenticationService.oauth(oauthRequest)).thenReturn(oauthResponse);

        ResponseEntity<OauthResponse> response = authenticationController.oauth(oauthRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(oauthResponse, response.getBody());
    }

    private void startOauth() {
        oauthRequest = new OauthRequest();
        oauthRequest.setEmail("test@email.com");
        oauthRequest.setPassword("123456789");

        oauthResponse = new OauthResponse();
        oauthResponse.setAccessToken("access_token");
    }
}