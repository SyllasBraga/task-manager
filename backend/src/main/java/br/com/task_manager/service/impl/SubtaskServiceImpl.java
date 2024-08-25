package br.com.task_manager.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.task_manager.exception.TaskManagerNotFoundException;
import br.com.task_manager.exception.enums.ExceptionsEnum;
import br.com.task_manager.model.SubtaskModel;
import br.com.task_manager.model.TaskModel;
import br.com.task_manager.repository.SubtaskRepository;
import br.com.task_manager.service.SubtaskService;

@Service
public class SubtaskServiceImpl implements SubtaskService{
    
    private static final Logger logger = LoggerFactory.getLogger(SubtaskServiceImpl.class);

    private final SubtaskRepository subtaskRepository;
    private final TaskServiceImpl taskServiceImpl;

    public SubtaskServiceImpl(SubtaskRepository subtaskRepository, TaskServiceImpl taskServiceImpl){
        this.subtaskRepository = subtaskRepository;
        this.taskServiceImpl = taskServiceImpl;
    }

    public SubtaskModel insertSubtask(Long taskId, SubtaskModel subtaskModel){
        logger.info(":: SubtaskServiceImpl.insertTask() - Request: {}, {}", taskId, subtaskModel.getId());
        TaskModel taskModel= taskServiceImpl.getById(taskId);
        subtaskModel.setTask(taskModel);
        subtaskModel.setCreatedDate(Timestamp.from(Instant.now()));
        SubtaskModel subtaskSaved = subtaskRepository.save(subtaskModel);
        logger.info(":: SubtaskServiceImpl.insertTask() - Response: {}", subtaskSaved.getId());
        return subtaskSaved;
    }

    @Override
    public SubtaskModel updateSubtask(Long id, SubtaskModel subtaskModel) {
        logger.info(":: SubtaskServiceImpl.updateSubtask() - Request: {}, {}", id, subtaskModel.getId());
        SubtaskModel subtaskSaved = getById(id);

        subtaskSaved.setCreatedDate(subtaskModel.getCreatedDate());
        subtaskSaved.setDeadlineDate(subtaskModel.getDeadlineDate());
        subtaskSaved.setDescription(subtaskModel.getDescription());
        subtaskSaved.setEndDate(subtaskModel.getEndDate());
        subtaskSaved.setPriority(subtaskModel.getPriority());
        subtaskSaved.setStatus(subtaskModel.getStatus());
        subtaskSaved.setTitle(subtaskModel.getTitle());

        subtaskModel = subtaskRepository.save(subtaskSaved);
        logger.info(":: SubtaskServiceImpl.updateSubtask() - Response: {}", subtaskModel.getId());
        return subtaskModel;
    }

    @Override
    public void deleteSubtask(Long id) {
        logger.info(":: SubtaskServiceImpl.updateSubtask() - Request: {}", id);
        SubtaskModel subtaskModel = getById(id);
        subtaskRepository.delete(subtaskModel);
    }

    private SubtaskModel getById(Long id){
        Optional<SubtaskModel> subtaskModel = subtaskRepository.findById(id);
        if (!subtaskModel.isPresent()) {
            throw new TaskManagerNotFoundException(ExceptionsEnum.NOT_FOUND.getMsg());
        }
        logger.info(":: SubtaskServiceImpl.getById() - SELECT RESULT: {}", subtaskModel.get().getId());
        return subtaskModel.get();
    }   
}
