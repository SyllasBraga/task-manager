package br.com.task_manager.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.task_manager.exception.TaskManagerNotFoundException;
import br.com.task_manager.model.StatusEnum;
import br.com.task_manager.model.SubtaskModel;
import br.com.task_manager.model.TaskModel;
import br.com.task_manager.repository.SubtaskRepository;
import br.com.task_manager.service.impl.SubtaskServiceImpl;
import br.com.task_manager.service.impl.TaskServiceImpl;

class SubtaskServiceImplTest {

    @InjectMocks
    private SubtaskServiceImpl subtaskService;

    @Mock
    private SubtaskRepository subtaskRepository;

    @Mock
    private TaskServiceImpl taskServiceImpl;

    private SubtaskModel subtaskModel;
    private TaskModel taskModel;
    private static final Long TASK_ID = 1L;
    private static final Long SUBTASK_ID = 1L;
    private static final Timestamp NOW = Timestamp.from(Instant.now());

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        startTask();
        startSubtask();
    }

    private void startTask() {
        taskModel = new TaskModel();
        taskModel.setId(TASK_ID);
        taskModel.setTitle("Test Task");
    }

    private void startSubtask() {
        subtaskModel = new SubtaskModel();
        subtaskModel.setId(SUBTASK_ID);
        subtaskModel.setTitle("Test Subtask");
        subtaskModel.setCreatedDate(NOW);
        subtaskModel.setTask(taskModel);
    }

    @Test
    void whenInsertSubtaskReturnsSubtask() {
        when(taskServiceImpl.getById(TASK_ID)).thenReturn(taskModel);
        when(subtaskRepository.save(any(SubtaskModel.class))).thenReturn(subtaskModel);

        SubtaskModel result = subtaskService.insertSubtask(TASK_ID, subtaskModel);

        assertNotNull(result);
        assertEquals("Test Subtask", result.getTitle());
        assertEquals(taskModel, result.getTask());
        verify(taskServiceImpl, times(1)).getById(TASK_ID);
        verify(subtaskRepository, times(1)).save(subtaskModel);
    }

    @Test
    void whenUpdateSubtaskReturnsUpdatedSubtask() {
        SubtaskModel updatedSubtask = new SubtaskModel();
        updatedSubtask.setTitle("Updated Title");
        updatedSubtask.setDescription("Updated Description");
        updatedSubtask.setStatus(StatusEnum.DONE);
        updatedSubtask.setEndDate(NOW);
        updatedSubtask.setDeadlineDate(NOW);

        when(subtaskRepository.findById(SUBTASK_ID)).thenReturn(Optional.of(subtaskModel));
        when(subtaskRepository.save(any(SubtaskModel.class))).thenReturn(updatedSubtask);

        SubtaskModel result = subtaskService.updateSubtask(SUBTASK_ID, updatedSubtask);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(StatusEnum.DONE, result.getStatus());
        verify(subtaskRepository, times(1)).findById(SUBTASK_ID);
    }

    @Test
    void whenDeleteSubtaskExecutesSuccessfully() {
        when(subtaskRepository.findById(SUBTASK_ID)).thenReturn(Optional.of(subtaskModel));

        subtaskService.deleteSubtask(SUBTASK_ID);

        verify(subtaskRepository, times(1)).delete(subtaskModel);
    }

    @Test
    void whenGetByIdThrowsException() {
        when(subtaskRepository.findById(SUBTASK_ID)).thenReturn(Optional.empty());

        assertThrows(TaskManagerNotFoundException.class, () -> subtaskService.deleteSubtask(SUBTASK_ID));

        verify(subtaskRepository, times(1)).findById(SUBTASK_ID);
    }
}

