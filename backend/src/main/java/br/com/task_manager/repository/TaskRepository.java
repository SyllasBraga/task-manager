package br.com.task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.task_manager.model.TaskModel;

import java.sql.Timestamp;
import java.util.List;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {

    @Query(value = "SELECT * FROM TASKS T INNER JOIN USERS U ON U.id = T.id WHERE U.email = :email", nativeQuery = true)
    List<TaskModel> findByUserEmail(String email);

    @Query(value = "SELECT DISTINCT t.* " +
            "FROM tasks t " +
            "LEFT JOIN subtasks st ON t.id = st.task_id " +
            "WHERE t.user_id = (SELECT id FROM users WHERE email = :userEmail) " +
            "AND (:status IS NULL OR t.status = :status) " +
            "AND (:createdDate IS NULL OR t.createddate BETWEEN :createdDate AND NOW()) " +
            "AND (:endDate IS NULL OR t.enddate = :endDate) " +
            "AND (:deadLineDate IS NULL OR t.deadlinedate = :deadLineDate) " +
            "AND (:priority IS NULL OR st.priority = :priority) " +
            "ORDER BY CASE WHEN st.priority = TRUE THEN 0 ELSE 1 END ",
            nativeQuery = true)
    List<TaskModel> findTasksByFilters(
            @Param("status") String status,
            @Param("createdDate") Timestamp createdDate,
            @Param("endDate") Timestamp endDate,
            @Param("deadLineDate") Timestamp deadLineDate,
            @Param("priority") Boolean priority,
            @Param("userEmail") String userEmail);
    
}
