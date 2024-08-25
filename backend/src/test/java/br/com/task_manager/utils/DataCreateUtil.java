package br.com.task_manager.utils;

import br.com.task_manager.api.request.OauthRequest;
import br.com.task_manager.model.Role;
import br.com.task_manager.model.UserModel;

public class DataCreateUtil {

    private static final String USER_EMAIL = "syllas@email.com";
    private static final String USER_PASSWORD = "12345678";

    public static UserModel createUser(){
        return new UserModel(1L, "Test Example", USER_EMAIL, USER_PASSWORD, Role.USER);
    }

    public static OauthRequest generateToken(){
        return new OauthRequest(USER_EMAIL, USER_PASSWORD);
    }
}
