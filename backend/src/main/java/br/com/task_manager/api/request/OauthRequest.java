package br.com.task_manager.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class OauthRequest {
    
    @Email(message = "Deve ser válido")
    @NotBlank(message = "Não pode ser nulo")
    private String email;
    @NotBlank(message = "Não pode ser nulo")
    private String password;
}
