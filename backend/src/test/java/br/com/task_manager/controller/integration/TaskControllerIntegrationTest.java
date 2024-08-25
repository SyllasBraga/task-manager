package br.com.task_manager.controller.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.task_manager.api.request.SubtaskRequest;
import br.com.task_manager.api.request.TaskRequest;
import br.com.task_manager.model.StatusEnum;
import br.com.task_manager.service.AuthenticationService;
import br.com.task_manager.utils.DataCreateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    private String accessToken;
    private TaskRequest taskRequest;

    @BeforeEach
    void setUp() {
        accessToken = authenticationService.oauth(DataCreateUtil.generateToken()).getAccessToken();
        taskRequest = createTask();
    }

    @Test
    void shouldInsertTaskSuccessfully() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/task-manager/api/v1/list")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Task Title"))
                .andExpect(jsonPath("$.description").value("Task Description"));
    }

    @Test
    void shouldGetAllTasksSuccessfully() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders.get("/task-manager/api/v1/list")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldUpdateTaskSuccessfully() throws Exception {
        int taskId = 1;
        taskRequest.setTitle("Updated Task Title");
        taskRequest.setDescription("Updated Task Description");

        mockMvc.perform(MockMvcRequestBuilders.put("/task-manager/api/v1/list/" + taskId)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task Title"))
                .andExpect(jsonPath("$.description").value("Updated Task Description"));
    }

    private TaskRequest createTask(){
        return new TaskRequest("Task Title", "Task Description", StatusEnum.DONE, Timestamp.from(Instant.now()), List.of(new SubtaskRequest()));
    }
}

