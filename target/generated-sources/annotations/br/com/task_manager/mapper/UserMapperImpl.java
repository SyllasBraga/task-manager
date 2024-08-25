package br.com.task_manager.mapper;

import br.com.task_manager.api.request.UserRequest;
import br.com.task_manager.api.response.UserResponse;
import br.com.task_manager.model.UserModel;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-25T11:33:09-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Azul Systems, Inc.)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse userModelToUserResponse(UserModel userModel) {
        if ( userModel == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( userModel.getId() );
        userResponse.name( userModel.getName() );
        userResponse.email( userModel.getEmail() );

        return userResponse.build();
    }

    @Override
    public UserModel userRequestToUserModel(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        UserModel userModel = new UserModel();

        userModel.setName( userRequest.getName() );
        userModel.setEmail( userRequest.getEmail() );
        userModel.setPassword( userRequest.getPassword() );

        return userModel;
    }
}
