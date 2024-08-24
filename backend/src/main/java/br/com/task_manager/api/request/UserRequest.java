package br.com.task_manager.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    @Size(min = 3, message = "Tamanho mínimo de 3")
    private String name;
    @Email(message = "Deve ser válido")
    @NotBlank(message = "Não pode ser nulo")
    private String email;
    @NotBlank(message = "Não pode ser nulo")
    @Size(min = 8,  message = "Tamanho mínimo de 8")
    private String password;
}
