package br.com.task_manager.api.response;

import java.sql.Timestamp;

import br.com.task_manager.model.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubtaskResponse {

    private Long id;
    private String title;
    private String description;
    private StatusEnum status;
    private Timestamp createdDate;
    private Timestamp deadlineDate;
    private Timestamp endDate;
    private Boolean priority;
}
