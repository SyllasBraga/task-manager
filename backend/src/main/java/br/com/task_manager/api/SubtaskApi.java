package br.com.task_manager.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.task_manager.api.request.SubtaskRequest;
import br.com.task_manager.api.response.SubtaskResponse;
import br.com.task_manager.exception.TaskManagerNotFoundException;
import br.com.task_manager.exception.error.ValidationError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RequestMapping("/task-manager/api/v1/task/list")
public interface SubtaskApi {

    @PostMapping("/{listId}/subtask")
    @Operation(summary = "Endpoint para inserir uma nova subtarefa", responses = {
            @ApiResponse(responseCode = "201", description = "CREATED", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SubtaskResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TaskManagerNotFoundException.class)) }),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class)) })
    })
    ResponseEntity<SubtaskResponse> insertSubtask(
            @PathVariable(value = "listId", required = true) @NotBlank(message = "{taskId} não pode ser nulo") String taskId,
            @RequestBody @Valid SubtaskRequest subtaskRequest);

    @PutMapping("/subtask/{subtaskId}")
    @Operation(summary = "Endpoint para atualizar uma subtarefa", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SubtaskResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TaskManagerNotFoundException.class)) }),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class)) })
    })
    ResponseEntity<SubtaskResponse> updateSubtask(
            @PathVariable(value = "subtaskId", required = true) @NotBlank(message = "{subtaskId} não pode ser nulo") String subtaskId,
            @RequestBody @Valid SubtaskRequest subtaskRequest);

    @DeleteMapping("/subtask/{subtaskId}")
    @Operation(summary = "Endpoint para excluir uma subtarefa", responses = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TaskManagerNotFoundException.class)) }),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class)) })
    })
    ResponseEntity<SubtaskResponse> deleteSubtask(
            @PathVariable(value = "subtaskId", required = true) @NotBlank(message = "{subtaskId} não pode ser nulo") String subtaskId);
}
