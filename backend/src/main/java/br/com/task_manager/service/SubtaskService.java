package br.com.task_manager.service;

import br.com.task_manager.model.SubtaskModel;

public interface SubtaskService {
    
    SubtaskModel insertSubtask(Long taskId, SubtaskModel subtaskModel);
    SubtaskModel updateSubtask(Long id, SubtaskModel subtaskModel);
    void deleteSubtask(Long id);
}
