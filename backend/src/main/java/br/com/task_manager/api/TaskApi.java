package br.com.task_manager.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.task_manager.api.request.TaskRequest;
import br.com.task_manager.api.response.TaskResponse;
import br.com.task_manager.exception.TaskManagerNotFoundException;
import br.com.task_manager.exception.error.ValidationError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/task-manager/api/list")
public interface TaskApi{

    @PostMapping
    @Operation(summary = "Endpoint para inserir uma nova tarefa", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class)) }),
        @ApiResponse(responseCode = "404", description = "NOT FOUND", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TaskManagerNotFoundException.class)) }),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class)) })
    })
    ResponseEntity<TaskResponse> insertTask(@RequestBody @Valid TaskRequest taskRequest);

    @GetMapping
    @Operation(summary = "Endpoint para listar todas as tarefas", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class)) }),
        @ApiResponse(responseCode = "404", description = "NOT FOUND", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TaskManagerNotFoundException.class)) }),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class)) })
    })
    ResponseEntity<List<TaskResponse>> getAll(@RequestParam(value = "status") String status,
            @RequestParam(value = "createdDate") String createdDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "deadLineDate") String deadLineDate,
            @RequestParam(value = "priority") String priority);
    
    @PutMapping("/{taskId}")
    @Operation(summary = "Endpoint para atualizar uma tarefa", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class)) }),
        @ApiResponse(responseCode = "404", description = "NOT FOUND", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TaskManagerNotFoundException.class)) }),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class))})
    })
    ResponseEntity<TaskResponse> updateTask(@Valid @RequestBody TaskRequest taskRequest,
            @PathVariable(value = "taskId") @NotBlank(message = "{taskId} não pode ser nulo") Long taskId);
}
