package br.com.task_manager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.task_manager.model.Role;
import br.com.task_manager.model.UserModel;
import br.com.task_manager.repository.UserRepository;
import br.com.task_manager.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModel getUserByLogin(String login) {
        logger.info(":: UserServiceImpl.getUserByLogin() - Request: {}", login);
        UserModel user = userRepository.findByEmail(login);
        logger.info(":: UserServiceImpl.getUserByLogin() - Response: {}", user);
        return user;
    }

    @Override
    public UserModel insertUser(UserModel userModel) {
        logger.info(":: UserServiceImpl.insertUser() - Request: {}", userModel);
        userModel.setRole(Role.USER);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userModel = userRepository.save(userModel);
        logger.info(":: UserServiceImpl.insertUser() - Response: {}", userModel);
        return userModel;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
                return getUserByLogin(login);
            }
        };
    }
}
