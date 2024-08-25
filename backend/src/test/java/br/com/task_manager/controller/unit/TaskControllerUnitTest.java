package br.com.task_manager.controller.unit;

import br.com.task_manager.api.request.TaskRequest;
import br.com.task_manager.api.response.TaskResponse;
import br.com.task_manager.controller.TaskController;
import br.com.task_manager.mapper.TaskMapper;
import br.com.task_manager.model.TaskModel;
import br.com.task_manager.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TaskControllerUnitTest {

    private TaskModel taskModel;
    private static final String TASK_TITLE = "Test Task";
    private static final String TASK_DESCRIPTION = "Test Description";
    private static final String TASK_ID = "1";

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskServiceImpl taskServiceImpl;

    @Spy
    private TaskMapper taskMapper = TaskMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startTask();
    }

    private void startTask() {
        taskModel = new TaskModel();
        taskModel.setTitle(TASK_TITLE);
        taskModel.setDescription(TASK_DESCRIPTION);
    }

    @Test
    void whenInsertTaskShouldReturnHttpStatusCreated() {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle(TASK_TITLE);
        taskRequest.setDescription(TASK_DESCRIPTION);

        when(taskMapper.taskRequestToTaskModel(any(TaskRequest.class))).thenReturn(taskModel);
        when(taskServiceImpl.insertTask(any(TaskModel.class))).thenReturn(taskModel);
        when(taskMapper.taskModelToTaskResponse(any(TaskModel.class))).thenReturn(new TaskResponse());

        ResponseEntity<TaskResponse> response = taskController.insertTask(taskRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(TaskResponse.class, response.getBody().getClass());
    }

    @Test
    void whenGetAllShouldReturnTaskList() {
        List<TaskModel> taskModelList = new ArrayList<>();
        taskModelList.add(taskModel);

        when(taskServiceImpl.getAll(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(taskModelList);
        when(taskMapper.taskModelToTaskResponse(any(TaskModel.class))).thenReturn(new TaskResponse());

        ResponseEntity<List<TaskResponse>> response = taskController.getAll(null, null, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenUpdateTaskShouldReturnHttpStatusOk() {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle(TASK_TITLE);
        taskRequest.setDescription(TASK_DESCRIPTION);

        when(taskMapper.taskRequestToTaskModel(any(TaskRequest.class))).thenReturn(taskModel);
        when(taskServiceImpl.updateTask(anyLong(), any(TaskModel.class))).thenReturn(taskModel);
        when(taskMapper.taskModelToTaskResponse(any(TaskModel.class))).thenReturn(new TaskResponse());

        ResponseEntity<TaskResponse> response = taskController.updateTask(taskRequest, TASK_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TaskResponse.class, response.getBody().getClass());
    }
}
