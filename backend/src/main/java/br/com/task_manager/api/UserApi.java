package br.com.task_manager.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.task_manager.api.request.UserRequest;
import br.com.task_manager.api.response.UserResponse;
import br.com.task_manager.exception.error.StandardError;
import br.com.task_manager.exception.error.ValidationError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/task-manager/api/v1")
public interface UserApi {

    @PostMapping("/user")
    @Operation(summary = "Endpoint para inserir um novo usuário", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)) }),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class)) })
    })
    ResponseEntity<UserResponse> insertUser(@RequestBody @Valid UserRequest userRequest);
}
