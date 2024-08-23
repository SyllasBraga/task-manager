package br.com.task_manager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
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
        logger.info(":: AuthenticationServiceImpl.oauth() - Request: {} ::", request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userService.getUserByLogin(request.getEmail());
        var jwt = jwtService.generateToken(user);
        logger.info(":: AuthenticationServiceImpl.oauth() - Response: {} ::", jwt);
        return OauthResponse.builder().accessToken(jwt).build();
    }
    
}
