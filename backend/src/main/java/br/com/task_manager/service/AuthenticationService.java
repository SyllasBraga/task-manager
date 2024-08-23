package br.com.task_manager.service;


import br.com.task_manager.api.request.OauthRequest;
import br.com.task_manager.api.response.OauthResponse;

public interface AuthenticationService {

    OauthResponse oauth(OauthRequest request);
}
