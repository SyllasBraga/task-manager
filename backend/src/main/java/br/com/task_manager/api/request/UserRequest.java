package br.com.task_manager.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequest {
    
    @NotEmpty
    @Size(min = 3)
    private String name;
    @Email
    @NotNull
    private String email;
    @NotEmpty
    @Size(min = 8)
    private String password;
}
