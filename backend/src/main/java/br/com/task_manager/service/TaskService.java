package br.com.task_manager.service;

import java.util.List;

import br.com.task_manager.model.TaskModel;

public interface TaskService {
    
    TaskModel insertTask(TaskModel taskModel);
    List<TaskModel> getAll();
    TaskModel updateTask(Long id, TaskModel taskModel);
}
