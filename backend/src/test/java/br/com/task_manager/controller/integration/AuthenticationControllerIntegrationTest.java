package br.com.task_manager.controller.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.task_manager.api.request.OauthRequest;
import br.com.task_manager.utils.DataCreateUtil;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String oauthUri = "/task-manager/api/v1/o-auth";
    private OauthRequest oauthRequest;

    @BeforeEach
    void setUp() {
        oauthRequest = DataCreateUtil.generateToken();
    }

    @Test
    void shouldReturnValidOauthResponse() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(oauthUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(oauthRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty()); 
    }
}
