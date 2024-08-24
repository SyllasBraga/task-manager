package br.com.task_manager.exception;

public class TaskManagerBadRequestException extends RuntimeException{
    
    public TaskManagerBadRequestException(String msg){
        super(msg);
    }
}
