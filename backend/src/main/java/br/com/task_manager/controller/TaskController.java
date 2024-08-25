package br.com.task_manager.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
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
public class TaskController implements TaskApi {

    private final TaskServiceImpl taskServiceImpl;

    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @Override
    public ResponseEntity<TaskResponse> insertTask(@Valid TaskRequest taskRequest) {
        TaskModel taskModel = TaskMapper.INSTANCE.taskRequestToTaskModel(taskRequest);
        taskModel = taskServiceImpl.insertTask(taskModel);
        return ResponseEntity.status(201).body(TaskMapper.INSTANCE.taskModelToTaskResponse(taskModel));
    }

    @Override
    public ResponseEntity<List<TaskResponse>> getAll(@RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "createdDate", required = false) String createdDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "deadLineDate", required = false) String deadLineDate,
            @RequestParam(value = "priority", required = false) String priority) {

        List<TaskModel> tasksList = taskServiceImpl.getAll(status, createdDate, endDate, deadLineDate, priority);
        List<TaskResponse> taskResponse = new ArrayList<>();
        tasksList.forEach(task -> taskResponse.add(TaskMapper.INSTANCE.taskModelToTaskResponse(task)));

        return ResponseEntity.ok(taskResponse);
    }

    @Override
    public ResponseEntity<TaskResponse> updateTask(@Valid TaskRequest taskRequest,
            @NotBlank(message = "{taskId} não pode ser nulo") String taskId) {

        TaskModel taskModel = TaskMapper.INSTANCE.taskRequestToTaskModel(taskRequest);
        taskModel = taskServiceImpl.updateTask(Long.valueOf(taskId), taskModel);
        return ResponseEntity.status(200).body(TaskMapper.INSTANCE.taskModelToTaskResponse(taskModel));
    }
}
