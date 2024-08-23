package br.com.task_manager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.task_manager.api.AuthenticationApi;
import br.com.task_manager.api.request.OauthRequest;
import br.com.task_manager.api.response.OauthResponse;
import br.com.task_manager.service.AuthenticationService;

@RestController
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public ResponseEntity<OauthResponse> oauth(OauthRequest oauthRequest) {
        return ResponseEntity.ok(authenticationService.oauth(oauthRequest));
    }

}