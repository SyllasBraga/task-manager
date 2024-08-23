package br.com.task_manager.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import br.com.task_manager.api.request.OauthRequest;
import br.com.task_manager.api.response.OauthResponse;
import br.com.task_manager.service.AuthenticationService;
import br.com.task_manager.service.JwtService;
import br.com.task_manager.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public OauthResponse oauth(OauthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userService.getUserByLogin(request.getEmail());
        var jwt = jwtService.generateToken(user);
        return OauthResponse.builder().accessToken(jwt).build();

    }
    
}
