package br.com.task_manager.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.task_manager.exception.TaskManagerBadRequestException;
import br.com.task_manager.exception.enums.ExceptionsEnum;
import br.com.task_manager.model.TaskModel;
import br.com.task_manager.model.UserModel;
import br.com.task_manager.repository.TaskRepository;
import br.com.task_manager.service.TaskService;
import jakarta.transaction.Transactional;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;
    private final UserServiceImpl userServiceImpl;

    public TaskServiceImpl(TaskRepository taskRepository, UserServiceImpl userServiceImpl) {
        this.taskRepository = taskRepository;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    @Transactional
    public TaskModel insertTask(TaskModel taskModel) {
        logger.info(":: TaskServiceImpl.insertTask() - Request {}", taskModel);
        UserModel userModel = getUserByEmail();
        taskModel.setUserModel(userModel);
        taskModel.setCreatedDate(Timestamp.from(Instant.now()));
        taskModel.getSubtasks().forEach(subtask -> {
            subtask.setTask(taskModel);
            subtask.setCreatedDate(Timestamp.from(Instant.now()));
        });
        TaskModel savedTaskModel = taskRepository.save(taskModel);
        logger.info(":: TaskServiceImpl.insertTask() - Response UserId: {}", savedTaskModel.getId());
        return savedTaskModel;
    }

    @Override
    public List<TaskModel> getAll(String status, String createdDate,String endDate,
            String deadLineDate,
            String priority) {
        Timestamp createdDateFormatted = stringToTimestamp(createdDate);
        Timestamp endDateFormatted = stringToTimestamp(endDate);
        Timestamp deadLineDateFormatted = stringToTimestamp(deadLineDate);
        String userEmail = getUserEmailFromToken();

        Boolean formattedPriority = priority != null ? Boolean.valueOf(priority) : null;
        logger.info("{}, {}, {}, {}, {}, {}", status,
        createdDateFormatted, endDateFormatted, 
        deadLineDateFormatted, formattedPriority, userEmail);

        return taskRepository.findTasksByFilters(status,
        createdDateFormatted, endDateFormatted, 
        deadLineDateFormatted, formattedPriority, userEmail);
    }

    @Override
    public TaskModel updateTask(Long id, TaskModel taskModel) {
        logger.info(" :: TaskServiceImpl.updateTask() - Request: {}", taskModel);
        TaskModel taskToUpdate = taskRepository.findById(id).get();
        taskToUpdate.setTitle(taskModel.getTitle());
        taskToUpdate.setDescription(taskModel.getDescription());
        taskToUpdate.setStatus(taskModel.getStatus());
        taskToUpdate.setEndDate(taskModel.getEndDate());
        taskToUpdate.setDeadLineDate(taskModel.getDeadLineDate());
        TaskModel taskUpdated = taskRepository.save(taskToUpdate);
        logger.info(" :: TaskServiceImpl.updateTask() - Response: {}", taskUpdated);
        return taskToUpdate;
    }

    private UserModel getUserByEmail(){
        return userServiceImpl.getUserByLogin(getUserEmailFromToken());
    }

    private String getUserEmailFromToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        logger.info(":: TaskServiceImpl.getUserEmailFromToken() - Response {}", email);
        return email;
    }

    private Timestamp stringToTimestamp(String dateToFormat){
        Timestamp dateFormatted = null;
        try{
            if(dateToFormat != null){
                dateFormatted = Timestamp.valueOf(dateToFormat);
            }
        }catch(IllegalArgumentException ex){
            throw new TaskManagerBadRequestException(ExceptionsEnum.INVALID_DATE.getMsg());
        }
        return dateFormatted;
    }
}
