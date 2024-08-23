package br.com.task_manager.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.task_manager.model.UserModel;

public interface UserService {
    
    UserModel insertUser(UserModel userModel);
    UserDetailsService userDetailsService();
    UserModel getUserByLogin(String login);
}
