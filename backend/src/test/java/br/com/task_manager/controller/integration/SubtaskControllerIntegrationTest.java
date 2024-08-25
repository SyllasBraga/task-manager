package br.com.task_manager.controller.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.task_manager.api.request.SubtaskRequest;
import br.com.task_manager.service.AuthenticationService;
import br.com.task_manager.utils.DataCreateUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class SubtaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    private String accessToken;
    private SubtaskRequest subtaskRequest;

    private static final String TASK_ID = "1";

    @BeforeEach
    void setUp() {
        accessToken = authenticationService.oauth(DataCreateUtil.generateToken()).getAccessToken();
        subtaskRequest = createSubtaskRequest();
    }

    @Test
    void shouldInsertSubtaskSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/task-manager/api/v1/task/list/{TASK_ID}/subtask", TASK_ID)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subtaskRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Subtask Title"))
                .andExpect(jsonPath("$.description").value("Subtask Description"));
    }

    @Test
    void shouldUpdateSubtaskSuccessfully() throws Exception {
        String subtaskId = "2";
        subtaskRequest.setTitle("Updated Subtask Title");
        subtaskRequest.setDescription("Updated Subtask Description");

        mockMvc.perform(MockMvcRequestBuilders.put("/task-manager/api/v1/task/list/subtask/{subtaskId}", subtaskId)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subtaskRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Subtask Title"))
                .andExpect(jsonPath("$.description").value("Updated Subtask Description"));
    }

    @Test
    void shouldDeleteSubtaskSuccessfully() throws Exception {
        String subtaskId = "1";

        mockMvc.perform(MockMvcRequestBuilders.delete("/task-manager/api/v1/task/list/subtask/{subtaskId}", subtaskId)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNoContent());
    }

    private SubtaskRequest createSubtaskRequest() {
        return new SubtaskRequest("Subtask Title", "Subtask Description", null, Timestamp.from(Instant.now()), true);
    }
}

