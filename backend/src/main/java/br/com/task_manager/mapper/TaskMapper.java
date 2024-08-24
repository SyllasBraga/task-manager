package br.com.task_manager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.task_manager.api.request.TaskRequest;
import br.com.task_manager.api.response.TaskResponse;
import br.com.task_manager.model.TaskModel;

@Mapper
public interface TaskMapper {
    
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskModel taskRequestToTaskModel(TaskRequest taskRequest);

    TaskResponse taskModelToTaskResponse(TaskModel taskModel);
}