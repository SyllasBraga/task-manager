package br.com.task_manager.controller.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.task_manager.api.request.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String insertUserUri = "/task-manager/api/v1/user";

    @Test
    void shouldInsertUserAndReturnCreated() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Test User");
        userRequest.setEmail("testuser@example.com");
        userRequest.setPassword("password123");

        mockMvc.perform(MockMvcRequestBuilders.post(insertUserUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value("testuser@example.com"));
    }
}
