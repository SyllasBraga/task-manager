package br.com.task_manager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.task_manager.exception.TaskManagerBadRequestException;
import br.com.task_manager.exception.TaskManagerNotFoundException;
import br.com.task_manager.exception.enums.ExceptionsEnum;
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
    public UserModel getUserByLogin(String login) throws TaskManagerNotFoundException{
        logger.info(":: UserServiceImpl.getUserByLogin() - Request: {}", login);
        UserModel user = userRepository.findByEmail(login);
        if(user == null){
            throw new TaskManagerNotFoundException(ExceptionsEnum.NOT_FOUND.getMsg());
        }
        logger.info(":: UserServiceImpl.getUserByLogin() - Response: {}", user);
        return user;
    }

    @Override
    public UserModel insertUser(UserModel userModel) {
        try{
            logger.info(":: UserServiceImpl.insertUser() - Request: {}", userModel);
            userModel.setRole(Role.USER);
            userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
            userModel = userRepository.save(userModel);
            logger.info(":: UserServiceImpl.insertUser() - Response: {}", userModel);
            return userModel;
        }catch(DataIntegrityViolationException ex){
            logger.error(":: UserServiceImpl.insertUser() - Response: {}", ex.toString());
            throw new TaskManagerBadRequestException(ExceptionsEnum.CONSTRAINT_VIOLATION.getMsg());
        }
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
