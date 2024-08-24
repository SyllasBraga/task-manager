package br.com.task_manager.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.task_manager.api.TaskApi;
import br.com.task_manager.api.request.TaskRequest;
import br.com.task_manager.api.response.TaskResponse;
import br.com.task_manager.mapper.TaskMapper;
import br.com.task_manager.model.TaskModel;
import br.com.task_manager.service.impl.TaskServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
public class TaskController implements TaskApi{

    private final TaskServiceImpl taskServiceImpl;

    public TaskController(TaskServiceImpl taskServiceImpl){
        this.taskServiceImpl = taskServiceImpl;
    }

    @Override
    public ResponseEntity<TaskResponse> insertTask(@Valid TaskRequest taskRequest) {
        TaskModel taskModel = TaskMapper.INSTANCE.taskRequestToTaskModel(taskRequest);
        taskModel = taskServiceImpl.insertTask(taskModel);
        return ResponseEntity.status(201).body(TaskMapper.INSTANCE.taskModelToTaskResponse(taskModel));
    }

    @Override
    public ResponseEntity<List<TaskResponse>> getAll(String status, String createdDate, String endDate,
            String deadLineDate, String priority) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public ResponseEntity<TaskResponse> updateTask(@Valid TaskRequest taskRequest,
            @NotBlank(message = "{taskId} não pode ser nulo") Long taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTask'");
    }
    
}
