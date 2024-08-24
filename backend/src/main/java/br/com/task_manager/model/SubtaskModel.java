package br.com.task_manager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "subtasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubtaskModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    private Timestamp createdDate;
    private Timestamp endDate;
    private Timestamp deadLineDate;
    private Boolean priority;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    @JsonIgnore
    private TaskModel task;
}
