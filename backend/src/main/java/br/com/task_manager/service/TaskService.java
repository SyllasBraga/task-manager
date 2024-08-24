package br.com.task_manager.service;

import java.util.List;
import br.com.task_manager.model.TaskModel;

public interface TaskService {
    
    TaskModel insertTask(TaskModel taskModel);
    List<TaskModel> getAll( String status, String createdDate, String endDate,
            String deadLineDate,
            String priority);
    TaskModel updateTask(Long id, TaskModel taskModel);
}
