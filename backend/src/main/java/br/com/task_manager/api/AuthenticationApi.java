package br.com.task_manager.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.task_manager.api.request.OauthRequest;
import br.com.task_manager.api.response.OauthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/task-manager/api/v1")
public interface AuthenticationApi {

    @PostMapping("/o-auth")
    @Operation(summary = "Endpoint de autenticação", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content ={
            @Content(mediaType = "application/json", schema = @Schema(implementation = OauthResponse.class))})
    })
    ResponseEntity<OauthResponse> oauth(@RequestBody @Valid OauthRequest oauthRequest);
}
