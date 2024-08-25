package br.com.task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.task_manager.model.SubtaskModel;

public interface SubtaskRepository extends JpaRepository<SubtaskModel, Long>{
    
}
