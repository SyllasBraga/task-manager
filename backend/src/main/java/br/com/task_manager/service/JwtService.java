package br.com.task_manager.service;

import org.springframework.security.core.userdetails.UserDetails;

import br.com.task_manager.model.UserModel;

public interface JwtService {
    
    String extractUserName(String token);
    String generateToken(UserModel userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
}
