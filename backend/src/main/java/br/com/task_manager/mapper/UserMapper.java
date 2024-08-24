package br.com.task_manager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.task_manager.api.request.UserRequest;
import br.com.task_manager.api.response.UserResponse;
import br.com.task_manager.model.UserModel;

@Mapper
public interface UserMapper {
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse userModelToUserResponse(UserModel userModel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    UserModel userRequestToUserModel(UserRequest userRequest);
}
