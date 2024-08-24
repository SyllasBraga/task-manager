package br.com.task_manager.api.response;

import java.sql.Timestamp;

import br.com.task_manager.model.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private StatusEnum status;
    private Timestamp createdDate;
    private Timestamp deadlineDate;
    private Timestamp endDate;
    private List<SubtaskResponse> subtasks;
}
