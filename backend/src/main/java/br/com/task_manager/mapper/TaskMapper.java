package br.com.task_manager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.task_manager.api.request.TaskRequest;
import br.com.task_manager.api.response.TaskResponse;
import br.com.task_manager.model.TaskModel;

@Mapper
public interface TaskMapper {
    
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userModel", ignore = true)
    @Mapping(source = "subtasks", target = "subtasks")
    TaskModel taskRequestToTaskModel(TaskRequest taskRequest);

    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "deadLineDate", target = "deadlineDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "subtasks", target = "subtasks")
    TaskResponse taskModelToTaskResponse(TaskModel taskModel);
}