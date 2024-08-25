package br.com.task_manager.controller.unit;

import br.com.task_manager.api.request.SubtaskRequest;
import br.com.task_manager.api.response.SubtaskResponse;
import br.com.task_manager.controller.SubtaskController;
import br.com.task_manager.mapper.SubtaskMapper;
import br.com.task_manager.model.SubtaskModel;
import br.com.task_manager.service.SubtaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class SubtaskControllerUnitTest {

    private SubtaskModel subtaskModel;
    private static final String SUBTASK_TITLE = "Test Subtask";
    private static final String SUBTASK_DESCRIPTION = "Test Description";
    private static final String SUBTASK_ID = "1";
    private static final String TASK_ID = "1";

    @InjectMocks
    private SubtaskController subtaskController;

    @Mock
    private SubtaskService subtaskService;

    @Spy
    private SubtaskMapper subtaskMapper = SubtaskMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startSubtask();
    }

    private void startSubtask() {
        subtaskModel = new SubtaskModel();
        subtaskModel.setTitle(SUBTASK_TITLE);
        subtaskModel.setDescription(SUBTASK_DESCRIPTION);
    }

    @Test
    void whenInsertSubtaskShouldReturnHttpStatusCreated() {
        SubtaskRequest subtaskRequest = new SubtaskRequest();
        subtaskRequest.setTitle(SUBTASK_TITLE);
        subtaskRequest.setDescription(SUBTASK_DESCRIPTION);

        when(subtaskMapper.subtaskRequestToSubtaskModel(any(SubtaskRequest.class))).thenReturn(subtaskModel);
        when(subtaskService.insertSubtask(anyLong(), any(SubtaskModel.class))).thenReturn(subtaskModel);
        when(subtaskMapper.subtaskModelToSubtaskResponse(any(SubtaskModel.class))).thenReturn(new SubtaskResponse());

        ResponseEntity<SubtaskResponse> response = subtaskController.insertSubtask(TASK_ID, subtaskRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(SubtaskResponse.class, response.getBody().getClass());
    }

    @Test
    void whenUpdateSubtaskShouldReturnHttpStatusOk() {
        SubtaskRequest subtaskRequest = new SubtaskRequest();
        subtaskRequest.setTitle(SUBTASK_TITLE);
        subtaskRequest.setDescription(SUBTASK_DESCRIPTION);

        when(subtaskMapper.subtaskRequestToSubtaskModel(any(SubtaskRequest.class))).thenReturn(subtaskModel);
        when(subtaskService.updateSubtask(anyLong(), any(SubtaskModel.class))).thenReturn(subtaskModel);
        when(subtaskMapper.subtaskModelToSubtaskResponse(any(SubtaskModel.class))).thenReturn(new SubtaskResponse());

        ResponseEntity<SubtaskResponse> response = subtaskController.updateSubtask(SUBTASK_ID, subtaskRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SubtaskResponse.class, response.getBody().getClass());
    }

    @Test
    void whenDeleteSubtaskShouldReturnHttpStatusNoContent() {
        ResponseEntity<SubtaskResponse> response = subtaskController.deleteSubtask(SUBTASK_ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

