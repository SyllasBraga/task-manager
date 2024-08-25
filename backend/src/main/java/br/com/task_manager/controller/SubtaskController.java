package br.com.task_manager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.task_manager.api.SubtaskApi;
import br.com.task_manager.api.request.SubtaskRequest;
import br.com.task_manager.api.response.SubtaskResponse;
import br.com.task_manager.mapper.SubtaskMapper;
import br.com.task_manager.model.SubtaskModel;
import br.com.task_manager.service.SubtaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
public class SubtaskController implements SubtaskApi {

    private final SubtaskService subtaskService;

    public SubtaskController(SubtaskService subtaskService) {
        this.subtaskService = subtaskService;
    }

    @Override
    public ResponseEntity<SubtaskResponse> insertSubtask(
            @NotBlank(message = "{taskId} não pode ser nulo") String taskId, @Valid SubtaskRequest subtaskRequest) {
        SubtaskModel subtaskModel = SubtaskMapper.INSTANCE.subtaskRequestToSubtaskModel(subtaskRequest);
        subtaskModel = subtaskService.insertSubtask(Long.valueOf(taskId), subtaskModel);
        SubtaskResponse subtaskResponse = SubtaskMapper.INSTANCE.subtaskModelToSubtaskResponse(subtaskModel);
        return ResponseEntity.status(201).body(subtaskResponse);
    }

    @Override
    public ResponseEntity<SubtaskResponse> updateSubtask(
            @NotBlank(message = "{subtaskId} não pode ser nulo") String subtaskId,
            @Valid SubtaskRequest subtaskRequest) {
        SubtaskModel subtaskModel = SubtaskMapper.INSTANCE.subtaskRequestToSubtaskModel(subtaskRequest);
        subtaskModel = subtaskService.updateSubtask(Long.valueOf(subtaskId), subtaskModel);
        SubtaskResponse subtaskResponse = SubtaskMapper.INSTANCE.subtaskModelToSubtaskResponse(subtaskModel);
        return ResponseEntity.status(200).body(subtaskResponse);
    }

    @Override
    public ResponseEntity<SubtaskResponse> deleteSubtask(
            @NotBlank(message = "{subtaskId} não pode ser nulo") String subtaskId) {
        subtaskService.deleteSubtask(Long.valueOf(subtaskId));
        return ResponseEntity.noContent().build();
    }
}
