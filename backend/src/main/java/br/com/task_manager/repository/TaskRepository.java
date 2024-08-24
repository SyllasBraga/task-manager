package br.com.task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.task_manager.model.TaskModel;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskModel, Long>{

    @Query(value = "SELECT * FROM TASKS T INNER JOIN USERS U ON U.id = T.id WHERE U.email = :email",
        nativeQuery = true)
    List<TaskModel> findByUserEmail(String email);
}
