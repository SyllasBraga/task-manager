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

import br.com.task_manager.model.Role;
import br.com.task_manager.model.UserModel;
import br.com.task_manager.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private UserModel userModel = new UserModel(1L, "", "test@example.com", "password123", Role.USER);

    private final String oauthUri = "/task-manager/api/v1/o-auth";

    @Test
    void shouldReturnValidOauthResponse() throws Exception {
        String requestBody = "{\"email\": \"test@example.com\", \"password\": \"password123\"}";
        userService.insertUser(userModel);

        mockMvc.perform(MockMvcRequestBuilders.post(oauthUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }
}
