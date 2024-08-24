package br.com.task_manager.exception.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldMessageError {

    private String fieldMessage;
    private String name;
}
