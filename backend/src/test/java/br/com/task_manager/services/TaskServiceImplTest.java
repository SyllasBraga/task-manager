package br.com.task_manager.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import br.com.task_manager.model.StatusEnum;
import br.com.task_manager.model.TaskModel;
import br.com.task_manager.model.UserModel;
import br.com.task_manager.repository.TaskRepository;
import br.com.task_manager.service.impl.TaskServiceImpl;
import br.com.task_manager.service.impl.UserServiceImpl;

class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserServiceImpl userServiceImpl;

    @Mock
    private Authentication authentication;

    private static final String USER_EMAIL = "test@example.com";
    private static final String TASK_TITLE = "Test Task";
    private static final String TASK_DESCRIPTION = "Test Description";
    private static final Timestamp NOW = Timestamp.from(Instant.now());

    private TaskModel taskModel;
    private UserModel userModel;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        startUser();
        startTask();
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn(USER_EMAIL);
    }

    private void startUser() {
        userModel = new UserModel();
        userModel.setEmail(USER_EMAIL);
    }

    private void startTask() {
        taskModel = new TaskModel();
        taskModel.setTitle(TASK_TITLE);
        taskModel.setDescription(TASK_DESCRIPTION);
        taskModel.setCreatedDate(NOW);
        taskModel.setUserModel(userModel);
        taskModel.setSubtasks(List.of());
    }

    @Test
    @Transactional
    void whenInsertTaskReturnsTask() {
        when(userServiceImpl.getUserByLogin(USER_EMAIL)).thenReturn(userModel);
        when(taskRepository.save(any(TaskModel.class))).thenReturn(taskModel);

        TaskModel result = taskService.insertTask(taskModel);

        assertNotNull(result);
        assertEquals(TASK_TITLE, result.getTitle());
        assertEquals(TASK_DESCRIPTION, result.getDescription());
        assertEquals(userModel, result.getUserModel());
        verify(userServiceImpl, times(1)).getUserByLogin(USER_EMAIL);
        verify(taskRepository, times(1)).save(taskModel);
    }

    @Test
    void whenGetAllReturnsTasks() {
        Timestamp createdDateFormatted = new Timestamp(0);
        Timestamp endDateFormatted = new Timestamp(0); 
        Timestamp deadLineDateFormatted = new Timestamp(0); 
        Boolean formattedPriority = true;

        when(userServiceImpl.getUserByLogin(USER_EMAIL)).thenReturn(userModel);
        when(taskRepository.findTasksByFilters(any(), eq(createdDateFormatted), eq(endDateFormatted), eq(deadLineDateFormatted), eq(formattedPriority), eq(USER_EMAIL)))
                .thenReturn(Arrays.asList(taskModel));

        List<TaskModel> result = taskService.getAll(null, null, null, null, Boolean.toString(formattedPriority));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(taskModel, result.get(0));
        verify(taskRepository, times(1)).findTasksByFilters(null, createdDateFormatted, endDateFormatted, deadLineDateFormatted, formattedPriority, USER_EMAIL);
    }

    @Test
    void whenUpdateTaskReturnsUpdatedTask() {
        TaskModel updatedTask = new TaskModel();
        updatedTask.setTitle("Updated Title");
        updatedTask.setDescription("Updated Description");
        updatedTask.setStatus(StatusEnum.DONE);
        updatedTask.setEndDate(NOW);
        updatedTask.setDeadLineDate(NOW);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskModel));
        when(taskRepository.save(any(TaskModel.class))).thenReturn(updatedTask);

        TaskModel result = taskService.updateTask(1L, updatedTask);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(StatusEnum.DONE, result.getStatus());
        assertEquals(NOW, result.getEndDate());
        assertEquals(NOW, result.getDeadLineDate());
        verify(taskRepository, times(1)).findById(1L);
    }
}

