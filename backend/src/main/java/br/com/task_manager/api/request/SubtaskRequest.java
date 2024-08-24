package br.com.task_manager.api.request;

import java.sql.Timestamp;

import br.com.task_manager.model.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class SubtaskRequest {

    @NotBlank(message = "Não pode estar em branco")
    @Size(message = "Tamanho mínimo de 3")
    private String title;
    @Size(message = "Tamanho mínimo de 10")
    @NotBlank(message = "Não pode estar em branco")
    private String description;
    private StatusEnum status;
    private Timestamp deadlineDate;
    @NotBlank(message = "Não pode estar em branco")
    private Boolean priority;
}
